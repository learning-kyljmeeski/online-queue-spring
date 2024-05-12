package com.kyljmeeski.onlinequeue.repository;

import com.kyljmeeski.onlinequeue.entity.Queue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QueueRepository extends CrudRepository<Queue, Long> {
    @Query("SELECT q FROM Queue q " +
            "LEFT JOIN q.people p " +
            "WHERE q.id = :queueId AND NOT EXISTS (" +
            "    SELECT 1 FROM Person p2 " +
            "    WHERE p2.queue = q AND p2.name = :personName" +
            ")")
    Optional<Queue> findQueueByIdIfNoPersonWithSameName(@Param("queueId") Long queueId, @Param("personName") String personName);
}
