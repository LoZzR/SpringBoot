package com.spring.boot.services;

import com.spring.boot.entities.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class Initializer {

    private Logger logger = LoggerFactory.getLogger(Initializer.class);
    private PersonService personService;

    @Autowired
    public Initializer(PersonService personService) {
        this.personService = personService;
    }

    @PostConstruct
    public void init() {


    }
}
