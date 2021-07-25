package com.afk.testtechnique.repository;

import com.afk.testtechnique.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.afk.testtechnique.TestConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
class UserRepositoryTest {

    private final static User USER = new User(USERNAME, FIRST_NAME, LAST_NAME, EMAIL);

    @Autowired
    UserRepository repository;

    @Test
    void shouldFindNoUserWhenRepositoryIsEmpty() {
        List<User> users = repository.findAll();
        assertThat(users, is(empty()));
    }

    @Test
    void shouldStoreUser() {
        User user = repository.save(USER);
        assertThat(user, hasProperty(USERNAME_PROPERTY, is(USERNAME)));
        assertThat(user, hasProperty(FIRST_NAME_PROPERTY, is(FIRST_NAME)));
        assertThat(user, hasProperty(LAST_NAME_PROPERTY, is(LAST_NAME)));
        assertThat(user, hasProperty(EMAIL_PROPERTY, is(EMAIL)));
    }

    @Test
    void shouldStoreUserWithDefaultCompany() {
        User user = repository.save(USER);
        assertThat(user, hasProperty(COMPANY_PROPERTY, is(DEFAULT_COMPANY)));
    }

    @Test
    void shouldStoreUserWithCustomCompany() {
        User user = new User(USERNAME, FIRST_NAME, LAST_NAME, EMAIL);
        user.setCompany("AKLM");
        User createdUser = repository.save(user);
        assertThat(createdUser, hasProperty(COMPANY_PROPERTY, is("AKLM")));
    }

    @Test
    void shouldFindUserByUserName() {
        repository.save(USER);
        Optional<User> user = repository.findByUsername(USERNAME);
        assertThat(user.get(), hasProperty(USERNAME_PROPERTY, is(USERNAME)));
        assertThat(user.get(), hasProperty(FIRST_NAME_PROPERTY, is(FIRST_NAME)));
        assertThat(user.get(), hasProperty(LAST_NAME_PROPERTY, is(LAST_NAME)));
        assertThat(user.get(), hasProperty(EMAIL_PROPERTY, is(EMAIL)));
    }
}
