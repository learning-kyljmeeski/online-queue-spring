package com.kyljmeeski.onlinequeue.entity;

import com.kyljmeeski.onlinequeue.model.request.CreateQueueRequest;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "queues")
public class Queue {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "queues_id_seq")
    @SequenceGenerator(name = "queues_id_seq", sequenceName = "queues_id_seq", allocationSize = 1)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
//    todo: columnDefinition doesn't working, find out why
    @Column(nullable = false, columnDefinition = "int default 10")
    private Integer duration;
    @OneToMany(mappedBy = "queue", cascade = CascadeType.ALL)
    private List<Person> people;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    protected Queue() {

    }

    public Queue(String name, String description, int duration) {
        this.name = name;
        this.description = description;
        this.duration = duration;
    }

    public Queue(CreateQueueRequest createQueueRequest) {
        this(createQueueRequest.name(), createQueueRequest.description(), createQueueRequest.duration());
    }

    public long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public List<String> people() {
        return people.stream().map(Person::name).collect(Collectors.toList());
    }

    public Person callNext() {
        return people.remove(0);
    }

    public void selectOwner(User user) {
        this.owner = user;
    }

    public long length() {
        return people.size();
    }
}
