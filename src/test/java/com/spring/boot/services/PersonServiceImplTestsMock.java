package com.spring.boot.services;

import com.spring.boot.entities.Person;
import com.spring.boot.repo.PersonRepo;
import com.spring.boot.util.DateProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.text.DateFormat;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//Not necessary for springboot
//@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTestsMock {

    public static final Long PERSON_ID = 5L;

    @Autowired
    DataSource ds;

    //@Mock //Creates mock instance of the field it annotates
    //private PersonRepo personRepo;

    //@InjectMocks
    @Autowired
    private PersonServiceImpl personService;


    @Test
    @Transactional
    @Sql(statements = {"INSERT INTO PERSON(ID, " +
            "USERNAME, FIRST_NAME,LAST_NAME, PASSWORD, " +
            "HIRING_DATE, VERSION, CREATED_AT, MODIFIED_AT)" +
            "VALUES (5, 'irene.adler', 'Irene', 'Adler', " +
            "'id123ds', '1990-08-18', 1, '1990-07-18', '1998-01-18');"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testFindByIdPositive() {
        //Mockito.when(personRepo.findById(anyLong())).thenReturn(Optional.of(new Person()));
        /*Person p = new Person();
        p.setUsername("irene.adler");
        p.setFirstName("Irene");
        p.setLastName("Adler");
        p.setPassword("id123ds");
        p.setHiringDate(DateProcessor.toDate("1990-08-18"));
        p.setCreatedAt(DateProcessor.toDate("1990-07-18"));
        p.setModifiedAt(DateProcessor.toDate("1998-01-18"));
        personService.save(p);*/
        Person person = personService.findById(PERSON_ID).get();
        assertNotNull(person);
    }
}
