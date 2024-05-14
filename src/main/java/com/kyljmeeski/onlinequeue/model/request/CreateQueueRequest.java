package com.kyljmeeski.onlinequeue.model.request;

public record CreateQueueRequest(String name, String description, int duration) {
}
