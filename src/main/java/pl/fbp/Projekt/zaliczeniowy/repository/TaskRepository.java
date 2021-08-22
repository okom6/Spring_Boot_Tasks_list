package pl.fbp.Projekt.zaliczeniowy.repository;

import org.springframework.data.repository.CrudRepository;
import pl.fbp.Projekt.zaliczeniowy.entity.Cathegory;
import pl.fbp.Projekt.zaliczeniowy.entity.Task;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Integer> {
}
