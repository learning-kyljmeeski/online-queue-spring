package com.kyljmeeski.onlinequeue.entity;

import com.kyljmeeski.onlinequeue.model.NewQueue;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "queues")
public class Queue {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private int duration;
    @OneToMany(mappedBy = "queue", cascade = CascadeType.ALL)
    private List<Person> people;

    protected Queue() {

    }

    public Queue(String name, String description, int duration) {
        this.name = name;
        this.description = description;
        this.duration = duration;
    }

    public Queue(NewQueue newQueue) {
        this(newQueue.name(), newQueue.description(), newQueue.duration());
    }

    public long id() {
        return id;
    }

    public List<String> people() {
        return people.stream().map(Person::name).collect(Collectors.toList());
    }

    public Person callNext() {
        return people.remove(0);
    }
}
