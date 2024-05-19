package com.kyljmeeski.onlinequeue.service.impl;

import com.kyljmeeski.onlinequeue.entity.Person;
import com.kyljmeeski.onlinequeue.entity.Queue;
import com.kyljmeeski.onlinequeue.entity.User;
import com.kyljmeeski.onlinequeue.exception.EmptyQueueException;
import com.kyljmeeski.onlinequeue.model.request.CreateQueueRequest;
import com.kyljmeeski.onlinequeue.model.response.AddPersonToQueueResponse;
import com.kyljmeeski.onlinequeue.model.response.QueueResponse;
import com.kyljmeeski.onlinequeue.repository.PersonRepository;
import com.kyljmeeski.onlinequeue.repository.QueueRepository;
import com.kyljmeeski.onlinequeue.repository.UserRepository;
import com.kyljmeeski.onlinequeue.service.QueueService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueueServiceImpl implements QueueService {
    private final PersonRepository personRepository;
    private final QueueRepository queueRepository;
    private final UserRepository userRepository;

    public QueueServiceImpl(
            PersonRepository personRepository, QueueRepository queueRepository, UserRepository userRepository
    ) {
        this.personRepository = personRepository;
        this.queueRepository = queueRepository;
        this.userRepository = userRepository;
    }

    @Override
    public long createQueue(CreateQueueRequest createQueueRequest) {
        User user = userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow(() -> new AuthenticationServiceException("User not found."));
        Queue queue = queueRepository.save(new Queue(createQueueRequest));
        queue.selectOwner(user);
        queueRepository.save(queue);
        return queue.id();
    }

    @Override
    public AddPersonToQueueResponse addPersonToQueue(String name, long id) {
        Queue queue = queueRepository.findQueueByIdIfNoPersonWithSameName(id, name).orElseThrow(
                () -> new EntityNotFoundException("Queue not found or person with such name is already on queue")
        );
        Person person = new Person(name);
        person.joinQueue(queue);
        personRepository.save(person);
        return new AddPersonToQueueResponse(queue.id(), person.name());
    }

    @Override
    public QueueResponse getPeopleOnQueue(long id) {
        Queue queue = queueRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Queue not found")
        );
        return new QueueResponse(queue.name(), queue.people());
    }

    @Override
    public List<String> callNextPersonOnQueue(long id) {
        try {
            Queue queue = queueRepository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("Queue not found")
            );
            Person person = queue.callNext();
            personRepository.delete(person);
            return queue.people();
        } catch (IndexOutOfBoundsException exception) {
            throw new EmptyQueueException();
        }
    }
}
