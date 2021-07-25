package com.afk.testtechnique.service;

import com.afk.testtechnique.exception.UserNotFoundException;
import com.afk.testtechnique.exception.UsernameExistException;
import com.afk.testtechnique.model.User;
import com.afk.testtechnique.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.afk.testtechnique.TestConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void shouldNotFindUser() {
        Optional<User> user = Optional.empty();
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        assertThrows(UserNotFoundException.class, () -> {
            userService.findByUsername("username");
        });
    }

    @Test
    void shouldReturnUser() {
        Optional<User> userOptional = Optional.of(new User(USERNAME, FIRST_NAME, LAST_NAME, EMAIL));
        when(userRepository.findByUsername(anyString())).thenReturn(userOptional);
        User user = userService.findByUsername(USERNAME);
        assertThat(user, hasProperty(USERNAME_PROPERTY, is(USERNAME)));
        assertThat(user, hasProperty(FIRST_NAME_PROPERTY, is(FIRST_NAME)));
        assertThat(user, hasProperty(LAST_NAME_PROPERTY, is(LAST_NAME)));
        assertThat(user, hasProperty(EMAIL_PROPERTY, is(EMAIL)));
    }

    @Test
    void shouldCreateUser() throws Exception {
        User userToCreate = new User(USERNAME, FIRST_NAME, LAST_NAME, EMAIL);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(userToCreate);
        User user = userService.createUser(userToCreate);
        assertThat(user, hasProperty(USERNAME_PROPERTY, is(USERNAME)));
        assertThat(user, hasProperty(FIRST_NAME_PROPERTY, is(FIRST_NAME)));
        assertThat(user, hasProperty(LAST_NAME_PROPERTY, is(LAST_NAME)));
        assertThat(user, hasProperty(EMAIL_PROPERTY, is(EMAIL)));
    }

    @Test
    void shouldNotReCreateExistingUser() throws Exception {
        User user = new User(USERNAME, FIRST_NAME, LAST_NAME, EMAIL);
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findByUsername(anyString())).thenReturn(optionalUser);
        assertThrows(UsernameExistException.class, () -> {
            userService.createUser(user);
        });
    }
}
