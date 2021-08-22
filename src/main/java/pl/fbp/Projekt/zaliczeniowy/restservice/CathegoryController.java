package pl.fbp.Projekt.zaliczeniowy.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pl.fbp.Projekt.zaliczeniowy.entity.Cathegory;
import pl.fbp.Projekt.zaliczeniowy.service.CathegoryService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.ui.Model;

@RestController
@RequestMapping("/cathegory")
public class CathegoryController {
    @Autowired
    private CathegoryService cathegoryService ;

    @GetMapping(value="/all" , produces="application/json")
    public String getAllCathegory(Map<String, Object> model) {
        model.put("elements", cathegoryService.findAll());
        return "cathegoryShowAll";
        //return ResponseEntity.ok(cathegoryService.findAll());
    }

    @GetMapping(value="/{id}", produces="application/json")
    public ResponseEntity<Cathegory> getCathegoryById(@PathVariable Integer id) {
        Optional<Cathegory> cathegory = cathegoryService.findById(id);
        return ResponseEntity.ok(cathegory.get());
    }

    @PostMapping(value="/add", produces="application/json")
    public Cathegory newCathegory(@RequestBody Cathegory newCathegory){
        return cathegoryService.createCathegory(newCathegory);
    }

    @PutMapping(value="/{id}", produces="application/json")
    public ResponseEntity<Cathegory> replacedCathegory(@RequestBody Cathegory newCathegory, @PathVariable Integer id){
        Optional<Cathegory> replacedCathegory = cathegoryService.replaceCathegory(newCathegory, id);
        return ResponseEntity.of(replacedCathegory);
    }

    @DeleteMapping("/{id}")
    public void deleteCathegory(@PathVariable Integer id){
        cathegoryService.deleteById(id);
    }

    /*@PatchMapping(value="/{id}", produces="application/json")
    public ResponseEntity<Cathegory> updateCathegory(@RequestBody Map<String, Object> updates, @PathVariable Integer id){
        Optional<Cathegory> updatedCathegory = cathegoryService.updateCathegory(updates, id);
        return ResponseEntity.of(updatedCathegory);
    }*/

    @GetMapping(value="/byname", produces="application/json")
    public List<Cathegory> getCathegoryByName(String name){
        return cathegoryService.findByName(name);
    }
}
