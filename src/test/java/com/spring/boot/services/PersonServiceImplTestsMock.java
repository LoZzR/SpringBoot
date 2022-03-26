package com.spring.boot.services;

import com.spring.boot.entities.Person;
import com.spring.boot.repo.PersonRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTestsMock {

    public static final Long PERSON_ID = 1L;

    @Autowired
    DataSource ds;

    @Mock //Creates mock instance of the field it annotates
    private PersonRepo personRepo;

    @InjectMocks
    private PersonServiceImpl personService;


    @Test
    public void testFindByIdPositive() {
        Mockito.when(personRepo.findById(anyLong())).thenReturn(Optional.of(new Person()));
        Person person = personService.findById(PERSON_ID).get();
        assertNotNull(person);
    }
}
