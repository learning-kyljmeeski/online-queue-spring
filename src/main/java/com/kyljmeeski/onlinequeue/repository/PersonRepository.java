package com.kyljmeeski.onlinequeue.repository;

import com.kyljmeeski.onlinequeue.entity.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
