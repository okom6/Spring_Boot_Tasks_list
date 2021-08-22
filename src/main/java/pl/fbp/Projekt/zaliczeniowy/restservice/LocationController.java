package pl.fbp.Projekt.zaliczeniowy.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fbp.Projekt.zaliczeniowy.entity.Cathegory;
import pl.fbp.Projekt.zaliczeniowy.entity.Location;
import pl.fbp.Projekt.zaliczeniowy.service.LocationService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    private LocationService locationService ;

    @GetMapping(value="/all" , produces="application/json")
    public ResponseEntity<List<Location>> getAllLocation() {
        return ResponseEntity.ok(locationService.findAll());
    }

    @GetMapping(value="/{id}", produces="application/json")
    public ResponseEntity<Location> getLocationById(@PathVariable Integer id) {
        Optional<Location> location = locationService.findById(id);
        return ResponseEntity.ok(location.get());
    }

    @PostMapping(value="/add", produces="application/json")
    public Location newLocation(@RequestBody Location newLocation){
        return locationService.createLocation(newLocation);
    }

    @PutMapping(value="/{id}", produces="application/json")
    public ResponseEntity<Location> replacedLocation(@RequestBody Location newLocation, @PathVariable Integer id){
        Optional<Location> replacedLocation = locationService.replaceLocation(newLocation, id);
        return ResponseEntity.of(replacedLocation);
    }

    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable Integer id){
        locationService.deleteById(id);
    }

    /*@PatchMapping(value="/{id}", produces="application/json")
    public ResponseEntity<Location> updateLocation(@RequestBody Map<String, Object> updates, @PathVariable Integer id){
        Optional<Location> updatedLocation = locationService.updateLocation(updates, id);
        return ResponseEntity.of(updatedLocation);
    }*/

    @GetMapping(value="/byname", produces="application/json")
    public List<Location> getLocationByName(String name){
        return locationService.findByName(name);
    }
}
