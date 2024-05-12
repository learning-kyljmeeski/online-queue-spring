package com.kyljmeeski.onlinequeue.service;

import com.kyljmeeski.onlinequeue.model.NewQueue;
import com.kyljmeeski.onlinequeue.model.PersonOnQueue;

import java.util.List;

public interface QueueService {
    long createQueue(NewQueue newQueue);

    PersonOnQueue addPersonToQueue(String name, long id);

    List<String> getPeopleOnQueue(long id);

    void callNextPersonOnQueue(long id);
}
