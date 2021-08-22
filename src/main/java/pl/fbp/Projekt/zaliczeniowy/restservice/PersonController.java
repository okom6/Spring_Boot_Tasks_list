package pl.fbp.Projekt.zaliczeniowy.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fbp.Projekt.zaliczeniowy.entity.Location;
import pl.fbp.Projekt.zaliczeniowy.entity.Person;
import pl.fbp.Projekt.zaliczeniowy.service.PersonService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService ;

    @GetMapping(value="/all" , produces="application/json")
    public ResponseEntity<List<Person>> getAllPerson() {
        return ResponseEntity.ok(personService.findAll());
    }

    @GetMapping(value="/{id}", produces="application/json")
    public ResponseEntity<Person> getPersonById(@PathVariable Integer id) {
        Optional<Person> person = personService.findById(id);
        return ResponseEntity.ok(person.get());
    }

    @PostMapping(value="/add", produces="application/json")
    public Person newPerson(@RequestBody Person newPerson){
        return personService.createPerson(newPerson);
    }

    @PutMapping(value="/{id}", produces="application/json")
    public ResponseEntity<Person> replacedPerson(@RequestBody Person newPerson, @PathVariable Integer id){
        Optional<Person> replacedPerson = personService.replacePerson(newPerson, id);
        return ResponseEntity.of(replacedPerson);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Integer id){
        personService.deleteById(id);
    }

    /*@PatchMapping(value="/{id}", produces="application/json")
    public ResponseEntity<Person> updateLocation(@RequestBody Map<String, Object> updates, @PathVariable Integer id){
        Optional<Person> updatedPerson = personService.updatePerson(updates, id);
        return ResponseEntity.of(updatedPerson);
    }*/

    @GetMapping(value="/byname", produces="application/json")
    public List<Person> getPersontByName(String name){
        return personService.findByName(name);
    }
}
