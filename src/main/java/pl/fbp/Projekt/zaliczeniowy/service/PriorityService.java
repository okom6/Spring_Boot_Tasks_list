package pl.fbp.Projekt.zaliczeniowy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fbp.Projekt.zaliczeniowy.entity.Person;
import pl.fbp.Projekt.zaliczeniowy.entity.Priority;
import pl.fbp.Projekt.zaliczeniowy.repository.PriorityRepository;

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
public class PriorityService {
    @Autowired
    private PriorityRepository priorityRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private EntityManagerFactory emf ;

    public List<Priority> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Priority> priorities = em.createQuery("SELECT priority from Priority priority").getResultList();
        em.close();
        return priorities ;
    }

    public Optional<Priority> findById(Integer priorityId) {
        Priority priority = em.find(Priority.class, priorityId);
        em.detach(priority);
        return Optional.of(priority);
    }

    @Transactional
    public Priority createPriority(Priority newPriority){
        em.persist(newPriority);
        return newPriority ;
    }

    @Transactional
    public Optional<Priority> replacePriority(Priority newPriority, Integer priorityId){
        Optional<Priority> priorityById = findById(priorityId);
        Priority emPriority = null ;

        if(priorityById.isPresent()){
            Priority priority = priorityById.get();
            priority.setName(newPriority.getName());

            emPriority = em.merge(priority);
        }

        return Optional.of(emPriority);
    }

    @Transactional
    public void deleteById(Integer priorityId){
        Priority priority = em.find(Priority.class, priorityId);
        em.remove(priority);
    }

    /*public Optional<Priority> updatePriority(Map<String, Object> updates, Integer priorityId){
        Optional<Priority> priorityById = priorityRepository.findById(priorityId);
        if(priorityById.isPresent()){
            Priority priority = priorityById.get();
            if(updates.containsKey("name")){
                priority.setName((String) updates.get("name"));
            }
            priorityRepository.save(priority);
        }
        return priorityById;
    }*/

    public List<Priority> findByName(String name){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Priority> cq = cb.createQuery(Priority.class);
        Root<Priority> from = cq.from(Priority.class);
        cq.select(from).where(cb.like(from.get("name"), "%" + name + "%"));
        TypedQuery<Priority> q = em.createQuery(cq);
        return q.getResultList();
    }
}