package pl.fbp.Projekt.zaliczeniowy.repository;

import org.springframework.data.repository.CrudRepository;
import pl.fbp.Projekt.zaliczeniowy.entity.Cathegory;
import pl.fbp.Projekt.zaliczeniowy.entity.Priority;

import java.util.List;

public interface PriorityRepository extends CrudRepository<Priority, Integer> {
    List<Priority> findByName(String name);
}
