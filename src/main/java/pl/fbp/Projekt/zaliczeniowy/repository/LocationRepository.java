package pl.fbp.Projekt.zaliczeniowy.repository;

import org.springframework.data.repository.CrudRepository;
import pl.fbp.Projekt.zaliczeniowy.entity.Cathegory;
import pl.fbp.Projekt.zaliczeniowy.entity.Location;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Integer> {
    List<Location> findByName(String name);
}
