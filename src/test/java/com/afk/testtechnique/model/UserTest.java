package com.afk.testtechnique.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.afk.testtechnique.TestConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
public class UserTest {

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void userShoulBeValid() {
        User user = new User(USERNAME, FIRST_NAME, LAST_NAME, EMAIL);
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations, hasSize(0));
    }

    @Test
    public void userWithoutUsernameShoulNotBeValid() {
        User user = new User(null, FIRST_NAME, LAST_NAME, EMAIL);
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations, hasSize(1));
    }

    @Test
    public void userWithoutFirstNameShoulNotBeValid() {
        User user = new User(USERNAME, null, LAST_NAME, EMAIL);
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations, hasSize(1));
    }

    @Test
    public void userWithoutLastNameShoulNotBeValid() {
        User user = new User(USERNAME, FIRST_NAME, null, EMAIL);
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations, hasSize(1));
    }

    @Test
    public void userWithoutEmailShoulNotBeValid() {
        User user = new User(USERNAME, FIRST_NAME, LAST_NAME, null);
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations, hasSize(1));
    }

    @Test
    public void userWithBadEmailShoulNotBeValid() {
        User user = new User(USERNAME, FIRST_NAME, LAST_NAME, "bad email");
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations, hasSize(1));
    }
}
