package pl.fbp.Projekt.zaliczeniowy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fbp.Projekt.zaliczeniowy.entity.Location;
import pl.fbp.Projekt.zaliczeniowy.entity.Person;
import pl.fbp.Projekt.zaliczeniowy.repository.PersonRepository;

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
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private EntityManagerFactory emf ;

    public List<Person> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Person> people = em.createQuery("SELECT person from Person person").getResultList();
        em.close();
        return people ;
    }

    public Optional<Person> findById(Integer personId) {
        Person person = em.find(Person.class, personId);
        em.detach(person);
        return Optional.of(person);
    }

    @Transactional
    public Person createPerson(Person newPerson){
        em.persist(newPerson);
        return newPerson ;
    }

    @Transactional
    public Optional<Person> replacePerson(Person newPerson, Integer personId){
        Optional<Person> personById = findById(personId);
        Person emPerson = null ;

        if(personById.isPresent()){
            Person person = personById.get();
            person.setName(newPerson.getName());

            emPerson = em.merge(person);
        }

        return Optional.of(emPerson);
    }

    @Transactional
    public void deleteById(Integer personId){
        Person person = em.find(Person.class, personId);
        em.remove(person);
    }

    /*public Optional<Person> updatePerson(Map<String, Object> updates, Integer personId){
        Optional<Person> personById = personRepository.findById(personId);
        if(personById.isPresent()){
            Person person = personById.get();
            if(updates.containsKey("name")){
                person.setName((String) updates.get("name"));
            }
            personRepository.save(person);
        }
        return personById;
    }*/

    public List<Person> findByName(String name){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        Root<Person> from = cq.from(Person.class);
        cq.select(from).where(cb.like(from.get("name"), "%" + name + "%"));
        TypedQuery<Person> q = em.createQuery(cq);
        return q.getResultList();
    }
}
