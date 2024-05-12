package com.kyljmeeski.onlinequeue.service.impl;

import com.kyljmeeski.onlinequeue.entity.Person;
import com.kyljmeeski.onlinequeue.entity.Queue;
import com.kyljmeeski.onlinequeue.model.NewQueue;
import com.kyljmeeski.onlinequeue.repository.PersonRepository;
import com.kyljmeeski.onlinequeue.repository.QueueRepository;
import com.kyljmeeski.onlinequeue.service.QueueService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueueServiceImpl implements QueueService {
    private final PersonRepository personRepository;
    private final QueueRepository queueRepository;

    public QueueServiceImpl(PersonRepository personRepository, QueueRepository queueRepository) {
        this.personRepository = personRepository;
        this.queueRepository = queueRepository;
    }

    @Override
    public long createQueue(NewQueue newQueue) {
        return queueRepository.save(new Queue(newQueue)).id();
    }

    @Override
    public void addPersonToQueue(String name, long id) {
        Person person = new Person(name);
        Queue queue = queueRepository.findById(id).orElseThrow();
        person.joinQueue(queue);
        personRepository.save(person);
    }

    @Override
    public List<String> getPeopleOnQueue(long id) {
        return queueRepository.findById(id).orElseThrow().people();
    }

    @Override
    public void callNextPersonOnQueue(long id) {
        Person person = queueRepository.findById(id).orElseThrow().callNext();
        personRepository.delete(person);
    }
}
