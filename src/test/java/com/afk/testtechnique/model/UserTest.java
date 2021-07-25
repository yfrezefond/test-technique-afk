package com.afk.testtechnique.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.afk.testtechnique.TestConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
class UserTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void userShoulBeValid() {
        User user = new User(USERNAME, FIRST_NAME, LAST_NAME, EMAIL);
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations, hasSize(0));
    }

    @Test
    void userWithoutUsernameShoulNotBeValid() {
        User user = new User(null, FIRST_NAME, LAST_NAME, EMAIL);
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations, hasSize(1));
    }

    @Test
    void userWithoutFirstNameShoulNotBeValid() {
        User user = new User(USERNAME, null, LAST_NAME, EMAIL);
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations, hasSize(1));
    }

    @Test
    void userWithoutLastNameShoulNotBeValid() {
        User user = new User(USERNAME, FIRST_NAME, null, EMAIL);
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations, hasSize(1));
    }

    @Test
    void userWithoutEmailShoulNotBeValid() {
        User user = new User(USERNAME, FIRST_NAME, LAST_NAME, null);
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations, hasSize(1));
    }

    @Test
    void userWithBadEmailShoulNotBeValid() {
        User user = new User(USERNAME, FIRST_NAME, LAST_NAME, "bad email");
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations, hasSize(1));
    }
}
