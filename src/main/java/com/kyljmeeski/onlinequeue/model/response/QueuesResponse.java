package com.kyljmeeski.onlinequeue.model.response;

import java.util.List;

public record QueuesResponse(List<Queue> queues) {
    public record Queue(Long id, String name) {
    }
}
