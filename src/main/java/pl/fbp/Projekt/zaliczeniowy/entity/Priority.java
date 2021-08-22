package pl.fbp.Projekt.zaliczeniowy.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.persistence.GenerationType;
import java.util.List;

@Entity
public class Priority {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int Id;
    private String name;

    @OneToMany(mappedBy = "priority", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Task> taskList;

    public void setId(int id) {
        Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public List<Task> getTaskList() {
        return taskList;
    }
}