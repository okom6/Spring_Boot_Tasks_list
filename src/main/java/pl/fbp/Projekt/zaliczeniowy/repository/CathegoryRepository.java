package pl.fbp.Projekt.zaliczeniowy.repository;

import org.springframework.data.repository.CrudRepository;
import pl.fbp.Projekt.zaliczeniowy.entity.Cathegory;

import java.util.List;

public interface CathegoryRepository extends CrudRepository<Cathegory, Integer> {
    List<Cathegory> findByName(String name);
}