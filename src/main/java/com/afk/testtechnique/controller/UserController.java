package com.afk.testtechnique.controller;


import com.afk.testtechnique.dto.UserDTO;
import com.afk.testtechnique.exception.UserNotFoundException;
import com.afk.testtechnique.exception.UsernameExistException;
import com.afk.testtechnique.model.User;
import com.afk.testtechnique.service.UserService;
import com.afk.testtechnique.supervision.Supervision;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private ModelMapper mapper;

    @Supervision
    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = mapper.map(userDTO, User.class);
        User createdUser = userService.createUser(user);
        UserDTO createdUserDTO = mapper.map(createdUser, UserDTO.class);
        return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
    }

    @Supervision
    @GetMapping("/users/{username}")
    public ResponseEntity<UserDTO> findUserByUsername(@NotBlank @PathVariable("username") String username) {
        User user = userService.findByUsername(username);
        UserDTO userDTO = mapper.map(user, UserDTO.class);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Supervision
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @Supervision
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UsernameExistException.class)
    public Map<String, String> handleUsernameExistException(UsernameExistException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", String.format("Username '%s' already exist", ex.getUsername()));
        return errors;
    }

    @Supervision
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String, String> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", String.format("Username '%s' not found", ex.getUsername()));
        return errors;
    }
}
