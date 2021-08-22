package pl.fbp.Projekt.zaliczeniowy.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fbp.Projekt.zaliczeniowy.entity.*;
import pl.fbp.Projekt.zaliczeniowy.service.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private CathegoryService cathegoryService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private PersonService personService;

    @Autowired
    private PriorityService priorityService;

    @GetMapping(value="/all" , produces="application/json")
    public ResponseEntity<List<Task>> getAllTask() {
        return ResponseEntity.ok(taskService.findAll());
    }

    @GetMapping(value="/{id}", produces="application/json")
    public ResponseEntity<Task> getTaskById(@PathVariable Integer id) {
        Optional<Task> task = taskService.findById(id);
        return ResponseEntity.ok(task.get());
    }

    @PostMapping(value="/add/{cathegoryId}/{locationId}/{personId}/{priorityId}", produces="application/json")
    public ResponseEntity<?> newTask(@PathVariable (value = "cathegoryId") Integer cathegoryId,
                                     @PathVariable (value = "locationId") Integer locationId,
                                     @PathVariable (value = "personId") Integer personId,
                                     @PathVariable (value = "priorityId") Integer priorityId,
                                     @RequestBody Task newTask){

        Optional<Cathegory> cathegory = cathegoryService.findById(cathegoryId);
        newTask.setCathegory(cathegory.get());
        Optional<Location> location = locationService.findById(locationId);
        newTask.setLocation(location.get());
        Optional<Person> person = personService.findById(personId);
        newTask.setPerson(person.get());
        Optional<Priority> priority = priorityService.findById(priorityId);
        newTask.setPriority(priority.get());

        taskService.createTask(newTask);
        URI uri = URI.create(String.format("api/task/%s", newTask.getId()));
        return ResponseEntity.created(uri).body("Added task");
    }

    @PutMapping(value="/{taskId}/{cathegoryId}/{locationId}/{personId}/{priorityId}", produces="application/json")
    public ResponseEntity<?> replacedTask(@PathVariable (value = "taskId") Integer taskId,
                                             @PathVariable (value = "cathegoryId") Integer cathegoryId,
                                             @PathVariable (value = "locationId") Integer locationId,
                                             @PathVariable (value = "personId") Integer personId,
                                             @PathVariable (value = "priorityId") Integer priorityId,
                                             @RequestBody Task newTask){

        Optional<Cathegory> cathegory = cathegoryService.findById(cathegoryId);
        newTask.setCathegory(cathegory.get());
        Optional<Location> location = locationService.findById(locationId);
        newTask.setLocation(location.get());
        Optional<Person> person = personService.findById(personId);
        newTask.setPerson(person.get());
        Optional<Priority> priority = priorityService.findById(priorityId);
        newTask.setPriority(priority.get());

        Optional<Task> replacedTask = taskService.replaceTask(newTask, taskId);
        URI uri = URI.create(String.format("api/task/%s", newTask.getId()));
        return ResponseEntity.created(uri).body("Changed task");
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Integer id){
        taskService.deleteById(id);
    }

    /*@PatchMapping(value="/{id}", produces="application/json")
    public ResponseEntity<Task> updateLocation(@RequestBody Map<String, Object> updates, @PathVariable Integer id){
        Optional<Task> updatedTask = taskService.updateTask(updates, id);
       return ResponseEntity.of(updatedTask);
    }*/
}
