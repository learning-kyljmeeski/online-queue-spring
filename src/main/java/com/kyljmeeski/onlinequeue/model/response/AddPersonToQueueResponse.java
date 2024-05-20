package com.kyljmeeski.onlinequeue.model.response;

public record AddPersonToQueueResponse(Long queueId, String queueName, long queueLength, String personName) {
}
