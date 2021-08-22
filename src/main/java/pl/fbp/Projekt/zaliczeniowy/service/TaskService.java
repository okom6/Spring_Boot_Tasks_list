package pl.fbp.Projekt.zaliczeniowy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fbp.Projekt.zaliczeniowy.entity.*;
import pl.fbp.Projekt.zaliczeniowy.repository.TaskRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Component
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private EntityManagerFactory emf ;

    public List<Task> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Task> tasks = em.createQuery("SELECT task from Task task").getResultList();
        em.close();
        return tasks ;
    }

    public Optional<Task> findById(Integer taskId) {
        Task task = em.find(Task.class, taskId);
        em.detach(task);
        return Optional.of(task);
    }

    @Transactional
    public Task createTask(Task newTask){
        em.persist(newTask);
        return newTask ;
    }

    @Transactional
    public Optional<Task> replaceTask(Task newTask, Integer taskId){
        Optional<Task> taskById = findById(taskId);
        Task emTask = null ;

        if(taskById.isPresent()){
            Task task = taskById.get();
            task.setDescription(newTask.getDescription());
            task.setDate(newTask.getDate());
            task.setCathegory(newTask.getCathegory());
            task.setLocation(newTask.getLocation());
            task.setPerson(newTask.getPerson());
            task.setPriority(newTask.getPriority());

            emTask = em.merge(task);
        }

        return Optional.of(emTask);
    }

    @Transactional
    public void deleteById(Integer taskId){
        Task task = em.find(Task.class, taskId);
        em.remove(task);
    }

    /*public Optional<Task> updateTask(Map<String, Object> updates, Integer taskId){
        Optional<Task> taskById = taskRepository.findById(taskId);
        if(taskById.isPresent()){
            Task task = taskById.get();
            if(updates.containsKey("description")){
                task.setDescription((String) updates.get("description"));
            }
            if(updates.containsKey("date")){
                task.setDate((Date) updates.get("date"));
            }
            if(updates.containsKey("cathegory")){
                task.setCathegory((Cathegory) updates.get("cathegory"));
            }
            if(updates.containsKey("location")){
                task.setLocation((Location) updates.get("location"));
            }
            if(updates.containsKey("person")){
                task.setPerson((Person) updates.get("person"));
            }
            if(updates.containsKey("priority")){
                task.setPriority((Priority) updates.get("priority"));
            }
            taskRepository.save(task);
        }
        return taskById;
    }*/

}