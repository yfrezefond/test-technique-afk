package com.afk.testtechnique.service;

import com.afk.testtechnique.exception.UserNotFoundException;
import com.afk.testtechnique.exception.UsernameExistException;
import com.afk.testtechnique.model.User;
import com.afk.testtechnique.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new UsernameExistException(user.getUsername());
        }
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new UserNotFoundException(username));
    }
}
