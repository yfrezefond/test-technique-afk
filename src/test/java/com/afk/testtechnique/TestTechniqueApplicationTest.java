package com.afk.testtechnique;

import com.afk.testtechnique.controller.UserController;
import com.afk.testtechnique.repository.UserRepository;
import com.afk.testtechnique.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class TestTechniqueApplicationTest {

	@Autowired
	private UserController userController;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
		assertThat(userController, is(not(nullValue())));
		assertThat(userService, is(not(nullValue())));
		assertThat(userRepository, is(not(nullValue())));
	}

}
