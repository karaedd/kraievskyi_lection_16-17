package com.kraievskyi.task.repository;

import com.kraievskyi.task.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PersonRepository extends MongoRepository<Person, String> {
    List<Person> findAllByFullNameEn(String fullName);
}
