package com.kyljmeeski.onlinequeue.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "people")
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "queue_id")
    private Queue queue;

    protected Person() {

    }

    public Person(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public void joinQueue(Queue queue) {
        this.queue = queue;
    }
}
