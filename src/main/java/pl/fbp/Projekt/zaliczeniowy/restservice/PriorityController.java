package pl.fbp.Projekt.zaliczeniowy.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fbp.Projekt.zaliczeniowy.entity.Person;
import pl.fbp.Projekt.zaliczeniowy.entity.Priority;
import pl.fbp.Projekt.zaliczeniowy.service.PriorityService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/priority")
public class PriorityController {
    @Autowired
    private PriorityService priorityService ;

    @GetMapping(value="/all" , produces="application/json")
    public ResponseEntity<List<Priority>> getAllPriority() {
        return ResponseEntity.ok(priorityService.findAll());
    }

    @GetMapping(value="/{id}", produces="application/json")
    public ResponseEntity<Priority> getPriorityById(@PathVariable Integer id) {
        Optional<Priority> priority = priorityService.findById(id);
        return ResponseEntity.ok(priority.get());
    }

    @PostMapping(value="/add", produces="application/json")
    public Priority newPriority(@RequestBody Priority newPriority){
        return priorityService.createPriority(newPriority);
    }

    @PutMapping(value="/{id}", produces="application/json")
    public ResponseEntity<Priority> replacedPriority(@RequestBody Priority newPriority, @PathVariable Integer id){
        Optional<Priority> replacedPriority = priorityService.replacePriority(newPriority, id);
        return ResponseEntity.of(replacedPriority);
    }

    @DeleteMapping("/{id}")
    public void deletePriority(@PathVariable Integer id){
        priorityService.deleteById(id);
    }

    /*@PatchMapping(value="/{id}", produces="application/json")
    public ResponseEntity<Priority> updateLocation(@RequestBody Map<String, Object> updates, @PathVariable Integer id){
        Optional<Priority> updatedPriority = priorityService.updatePriority(updates, id);
        return ResponseEntity.of(updatedPriority);
    }*/

    @GetMapping(value="/byname", produces="application/json")
    public List<Priority> getPriorityByName(String name){
        return priorityService.findByName(name);
    }
}
