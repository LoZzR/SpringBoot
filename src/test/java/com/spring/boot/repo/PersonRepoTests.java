package com.spring.boot.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PersonRepoTests {

    @Autowired
    DataSource dataSource;

    @Autowired
    PersonRepo personRepo;

    @BeforeEach
    public void setUp(){
        assertNotNull(personRepo);
    }

    @Test
    @Sql(statements = {"INSERT INTO PERSON(ID, " +
            "USERNAME, FIRST_NAME,LAST_NAME, PASSWORD, " +
            "HIRING_DATE, VERSION, CREATED_AT, MODIFIED_AT)" +
            "VALUES (5, 'irene.adler', 'Irene', 'Adler', " +
            "'id123ds', '1990-08-18', 1, '1990-07-18', '1998-01-18');"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testFindByUsernamePositive(){
        assertNotNull(personRepo.findByUsername("irene.adler").get());
    }

}
