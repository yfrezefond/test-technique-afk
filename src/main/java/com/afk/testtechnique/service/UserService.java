package com.afk.testtechnique.service;

import com.afk.testtechnique.model.User;

public interface UserService {
    User createUser(User user);
    User findByUsername(String username);
}
