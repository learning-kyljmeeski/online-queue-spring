package com.kyljmeeski.onlinequeue.controller;

import com.kyljmeeski.onlinequeue.model.request.CreateQueueRequest;
import com.kyljmeeski.onlinequeue.model.response.AddPersonToQueueResponse;
import com.kyljmeeski.onlinequeue.service.QueueService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/queues")
public class QueueController {
    private final QueueService service;

    public QueueController(QueueService service) {
        this.service = service;
    }

    @PostMapping
    public long createQueue(@RequestBody CreateQueueRequest createQueueRequest) {
        return service.createQueue(createQueueRequest);
    }

    @PutMapping("/{id}")
    public AddPersonToQueueResponse addPersonToQueue(@PathVariable long id, @RequestParam String name) {
        return service.addPersonToQueue(name, id);
    }

    @GetMapping("/{id}")
    public List<String> getPeopleOnQueue(@PathVariable long id) {
        return service.getPeopleOnQueue(id);
    }

    @PutMapping("/{id}/next")
    public void callNextPersonOnQueue(@PathVariable long id) {
        service.callNextPersonOnQueue(id);
    }
}
