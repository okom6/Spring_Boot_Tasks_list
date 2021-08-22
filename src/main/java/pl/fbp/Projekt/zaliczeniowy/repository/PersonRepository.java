package pl.fbp.Projekt.zaliczeniowy.repository;

import org.springframework.data.repository.CrudRepository;
import pl.fbp.Projekt.zaliczeniowy.entity.Cathegory;
import pl.fbp.Projekt.zaliczeniowy.entity.Person;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    List<Person> findByName(String name);
}
