package pl.fbp.Projekt.zaliczeniowy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fbp.Projekt.zaliczeniowy.entity.Cathegory;
import pl.fbp.Projekt.zaliczeniowy.repository.CathegoryRepository;

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
public class CathegoryService {
    @Autowired
    private CathegoryRepository cathegoryRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private EntityManagerFactory emf ;

    public List<Cathegory> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Cathegory> cathegories = em.createQuery("SELECT cathegory from Cathegory cathegory").getResultList();
        em.close();
        return cathegories ;
    }

    public Optional<Cathegory> findById(Integer cathegoryId) {
        Cathegory cathegory = em.find(Cathegory.class, cathegoryId);
        em.detach(cathegory);
        return Optional.of(cathegory);
    }

    @Transactional
    public Cathegory createCathegory(Cathegory newCathegory){
        em.persist(newCathegory);
        return newCathegory ;
    }

    @Transactional
    public Optional<Cathegory> replaceCathegory(Cathegory newCathegory, Integer cathegoryId){
        Optional<Cathegory> cathegoryById = findById(cathegoryId);
        Cathegory emCathegory = null ;

        if(cathegoryById.isPresent()){
            Cathegory cathegory = cathegoryById.get();
            cathegory.setName(newCathegory.getName());

            emCathegory = em.merge(cathegory);
        }

        return Optional.of(emCathegory);
    }

    @Transactional
    public void deleteById(Integer cathegoryId){
        Cathegory cathegory = em.find(Cathegory.class, cathegoryId);
        em.remove(cathegory);
    }

    /*public Optional<Cathegory> updateCathegory(Map<String, Object> updates, Integer cathegoryId){
        Optional<Cathegory> cathegoryById = cathegoryRepository.findById(cathegoryId);
        if(cathegoryById.isPresent()){
            Cathegory cathegory = cathegoryById.get();
            if(updates.containsKey("name")){
                cathegory.setName((String) updates.get("name"));
            }
            cathegoryRepository.save(cathegory);
        }
        return cathegoryById;
    }*/

    public List<Cathegory> findByName(String name){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cathegory> cq = cb.createQuery(Cathegory.class);
        Root<Cathegory> from = cq.from(Cathegory.class);
        cq.select(from).where(cb.like(from.get("name"), "%" + name + "%"));
        TypedQuery<Cathegory> q = em.createQuery(cq);
        return q.getResultList();
    }
}

