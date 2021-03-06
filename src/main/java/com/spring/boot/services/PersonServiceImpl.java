package com.spring.boot.services;

import com.spring.boot.entities.Person;
import com.spring.boot.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {
    private PersonRepo personRepo;

    @Autowired
    public PersonServiceImpl(PersonRepo personRepo, @Value("${spring.boot.test.field}") String justForTest) {
        this.personRepo = personRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Person> findAll() {
        Set<Person> persons = new HashSet<>();
        persons.addAll(personRepo.findAll());
        return persons;
    }

    @Override
    public long count() {
        return personRepo.count();
    }

    @Override
    public Optional<Person> findById(Long id) {
        return personRepo.findById(id);
    }

    @Override
    public Person save(Person person) {
        return personRepo.save(person);
    }

    @Override
    public void delete(Person person) {
        personRepo.delete(person);
    }

    @Override
    public Optional<Person> findByCompleteName(String firstName, String lastName) {
        return personRepo.findByCompleteName(firstName,lastName);
    }

}
