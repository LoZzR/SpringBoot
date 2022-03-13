package com.spring.boot;

import com.spring.boot.entities.Person;
import com.spring.boot.services.PersonService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ApplicationTests {

	private Logger logger = LoggerFactory.getLogger(ApplicationTests.class);

	@Autowired
	private PersonService personService;

	@Autowired
	private DataSource ds;

	@Test
	void contextLoads() {
	}

	@Test
	void testFindAll() {
		Set<Person> persons = personService.findAll();
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>testFindAll : {}", persons);
		assertNotNull(persons);
	}

}
