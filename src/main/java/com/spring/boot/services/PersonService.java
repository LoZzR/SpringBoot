package com.spring.boot.services;


import com.spring.boot.entities.Person;

import java.util.Optional;
import java.util.Set;

public interface PersonService {
    Set findAll();

    long count();

    Optional<Person> findById(Long id);

    Person save(Person person);

    void delete(Person person);

    Optional<Person> findByCompleteName(String firstName, String lastName);
}
