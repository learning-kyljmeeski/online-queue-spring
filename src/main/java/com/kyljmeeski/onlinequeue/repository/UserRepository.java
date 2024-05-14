package com.kyljmeeski.onlinequeue.repository;

import com.kyljmeeski.onlinequeue.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT ((SELECT COUNT(*) FROM queues WHERE queues.user_id = :userId AND queues.id = :queueId) > 0)", nativeQuery = true)
    boolean checkIfUserHasSuchQueue(@Param("userId") long userId, @Param("queueId") long queueId);
}
