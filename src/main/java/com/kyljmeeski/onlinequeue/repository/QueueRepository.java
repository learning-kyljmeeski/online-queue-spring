package com.kyljmeeski.onlinequeue.repository;

import com.kyljmeeski.onlinequeue.entity.Queue;
import org.springframework.data.repository.CrudRepository;

public interface QueueRepository extends CrudRepository<Queue, Long> {
}
