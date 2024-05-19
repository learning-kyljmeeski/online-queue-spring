package com.kyljmeeski.onlinequeue.model.response;

import java.util.List;

public record QueueResponse(String name, List<String> people) {
}
