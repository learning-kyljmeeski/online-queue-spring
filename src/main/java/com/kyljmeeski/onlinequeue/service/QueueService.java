package com.kyljmeeski.onlinequeue.service;

import com.kyljmeeski.onlinequeue.model.request.CreateQueueRequest;
import com.kyljmeeski.onlinequeue.model.response.AddPersonToQueueResponse;

import java.util.List;

public interface QueueService {
    long createQueue(CreateQueueRequest createQueueRequest);

    AddPersonToQueueResponse addPersonToQueue(String name, long id);

    List<String> getPeopleOnQueue(long id);

    void callNextPersonOnQueue(long id);
}
