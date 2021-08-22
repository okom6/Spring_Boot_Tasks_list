package pl.fbp.Projekt.zaliczeniowy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fbp.Projekt.zaliczeniowy.entity.Cathegory;
import pl.fbp.Projekt.zaliczeniowy.entity.Location;
import pl.fbp.Projekt.zaliczeniowy.repository.LocationRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Component
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private EntityManagerFactory emf ;

    public List<Location> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Location> locations = em.createQuery("SELECT location from Location location").getResultList();
        em.close();
        return locations ;
    }

    public Optional<Location> findById(Integer locationId) {
        Location location = em.find(Location.class, locationId);
        em.detach(location);
        return Optional.of(location);
    }

    @Transactional
    public Location createLocation(Location newLocation){
        em.persist(newLocation);
        return newLocation ;
    }

    @Transactional
    public Optional<Location> replaceLocation(Location newLocation, Integer locationId){
        Optional<Location> locationById = findById(locationId);
        Location emLocation = null ;

        if(locationById.isPresent()){
            Location location = locationById.get();
            location.setName(newLocation.getName());

            emLocation = em.merge(location);
        }

        return Optional.of(emLocation);
    }

    @Transactional
    public void deleteById(Integer locationId){
        Location location = em.find(Location.class, locationId);
        em.remove(location);
    }

    /*public Optional<Location> updateLocation(Map<String, Object> updates, Integer locationId){
        Optional<Location> locationById = locationRepository.findById(locationId);
        if(locationById.isPresent()){
            Location location = locationById.get();
            if(updates.containsKey("name")){
                location.setName((String) updates.get("name"));
            }
            locationRepository.save(location);
        }
        return locationById;
    }*/

    public List<Location> findByName(String name){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Location> cq = cb.createQuery(Location.class);
        Root<Location> from = cq.from(Location.class);
        cq.select(from).where(cb.like(from.get("name"), "%" + name + "%"));
        TypedQuery<Location> q = em.createQuery(cq);
        return q.getResultList();
    }
}
