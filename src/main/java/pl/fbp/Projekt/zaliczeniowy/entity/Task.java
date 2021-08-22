package pl.fbp.Projekt.zaliczeniowy.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int Id;
    private String description;
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cathegory_id")
    private Cathegory cathegory;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "priority_id")
    private Priority priority;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "location_id")
    private Location location;

    public void setId(int id) {
        Id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCathegory(Cathegory cathegory) {
        this.cathegory = cathegory;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getId() {
        return Id;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public Cathegory getCathegory() {
        return cathegory;
    }

    public Priority getPriority() {
        return priority;
    }

    public Person getPerson() {
        return person;
    }

    public Location getLocation() {
        return location;
    }
}