package com.kyljmeeski.onlinequeue.service.impl;

import com.kyljmeeski.onlinequeue.entity.Person;
import com.kyljmeeski.onlinequeue.entity.Queue;
import com.kyljmeeski.onlinequeue.exception.EmptyQueueException;
import com.kyljmeeski.onlinequeue.model.NewQueue;
import com.kyljmeeski.onlinequeue.repository.PersonRepository;
import com.kyljmeeski.onlinequeue.repository.QueueRepository;
import com.kyljmeeski.onlinequeue.service.QueueService;
import jakarta.persistence.EntityNotFoundException;
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
        Queue queue = queueRepository.findQueueByIdIfNoPersonWithSameName(id, name).orElseThrow(
                () -> new EntityNotFoundException("Queue not found or person with such name is already on queue")
        );
        Person person = new Person(name);
        person.joinQueue(queue);
        personRepository.save(person);
    }

    @Override
    public List<String> getPeopleOnQueue(long id) {
        return queueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Queue not found")).people();
    }

    @Override
    public void callNextPersonOnQueue(long id) {
        try {
            Person person = queueRepository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("Queue not found")
            ).callNext();
            personRepository.delete(person);
        } catch (IndexOutOfBoundsException exception) {
            throw new EmptyQueueException("There is no one to call next");
        }
    }
}
