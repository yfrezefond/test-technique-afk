package com.afk.testtechnique.exception;

/**
 * Exception indicating that the user was not found
 */
public class UserNotFoundException extends RuntimeException {
    private final String username;

    public UserNotFoundException(String username) {
        super();
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
