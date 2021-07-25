package com.afk.testtechnique.controller;

import com.afk.testtechnique.exception.UserNotFoundException;
import com.afk.testtechnique.exception.UsernameExistException;
import com.afk.testtechnique.model.User;
import com.afk.testtechnique.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.afk.testtechnique.TestConstants.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void shouldNotFindUser() throws Exception {
        when(userService.findByUsername(anyString())).thenThrow(new UserNotFoundException(USERNAME));
        mockMvc.perform(get(String.format("/api/users/%s", USERNAME))).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(String.format("Username '%s' not found", USERNAME))));
    }

    @Test
    void shouldReturnUser() throws Exception {
        User user = new User(USERNAME, FIRST_NAME, LAST_NAME, EMAIL);
        when(userService.findByUsername(anyString())).thenReturn(user);
        mockMvc.perform(get(String.format("/api/users/%s", USERNAME))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(USERNAME)))
                .andExpect(jsonPath("$.firstName", is(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.email", is(EMAIL)));
    }

    @Test
    void shouldCreateUser() throws Exception {
        User user = new User(USERNAME, FIRST_NAME, LAST_NAME, EMAIL);
        when(userService.createUser(any(User.class))).thenReturn(user);
        String jsonUser = mapper.writeValueAsString(user);
        mockMvc.perform(post("/api/users").content(jsonUser).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(USERNAME)))
                .andExpect(jsonPath("$.firstName", is(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.email", is(EMAIL)));
    }

    @Test
    void shouldNotReCreateExistingUser() throws Exception {
        when(userService.createUser(any(User.class))).thenThrow(new UsernameExistException(USERNAME));
        User user = new User(USERNAME, FIRST_NAME, LAST_NAME, EMAIL);
        String jsonUser = mapper.writeValueAsString(user);
        mockMvc.perform(post("/api/users").content(jsonUser).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is(String.format("Username '%s' already exist", USERNAME))));
    }

    @Test
    void shouldNotCreateUserWithInvalidParameters() throws Exception {
        User user = new User(null, FIRST_NAME, LAST_NAME, EMAIL);
        String jsonUser = mapper.writeValueAsString(user);
        mockMvc.perform(post("/api/users").content(jsonUser).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username", is(String.format("is mandatory", USERNAME))));
    }

    @Test
    void shouldNotCreateUserWithBadEmail() throws Exception {
        User user = new User(USERNAME, FIRST_NAME, LAST_NAME, "bad email");
        String jsonUser = mapper.writeValueAsString(user);
        mockMvc.perform(post("/api/users").content(jsonUser).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email", is(String.format("should be a valid email", USERNAME))));
    }
}
