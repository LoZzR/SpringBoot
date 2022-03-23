package com.spring.boot.services;

import com.spring.boot.entities.Person;
import com.spring.boot.repo.PersonRepo;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

//For Spring 3 + JUnit 4 tests
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class PersonServiceImplTests {

    public static final Long PERSON_ID = 1L;

    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Autowired
    PersonRepo personRepo;

    @Autowired
    PersonService personService;

    @Test
    public void testFindByIdPositive() {
        Mockito.when(personRepo.findById(anyLong())).thenReturn(Optional.of(new Person()));
        Person person = personService.findById(PERSON_ID).get();
        assertNotNull(person);
    }

    @Configuration
    static class MockCtxConfig {

        @Bean
        PersonRepo personRepo() {
            return mock(PersonRepo.class);
        }

        @Bean
        PersonService personService() {
            return new PersonServiceImpl(personRepo());
        }
    }
}
