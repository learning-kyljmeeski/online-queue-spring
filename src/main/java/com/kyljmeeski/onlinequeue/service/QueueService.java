package com.kyljmeeski.onlinequeue.service;

import com.kyljmeeski.onlinequeue.model.request.CreateQueueRequest;
import com.kyljmeeski.onlinequeue.model.response.AddPersonToQueueResponse;
import com.kyljmeeski.onlinequeue.model.response.QueueResponse;
import com.kyljmeeski.onlinequeue.model.response.QueuesResponse;

import java.util.List;

public interface QueueService {
    long createQueue(CreateQueueRequest createQueueRequest);

    AddPersonToQueueResponse addPersonToQueue(String name, long id);

    QueueResponse getPeopleOnQueue(long id);

    List<String> callNextPersonOnQueue(long id);

    QueuesResponse getQueues();
}
