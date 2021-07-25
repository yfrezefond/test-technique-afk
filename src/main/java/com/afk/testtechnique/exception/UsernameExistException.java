package com.afk.testtechnique.exception;

/**
 * Exception indicating that the username already exists
 */
public class UsernameExistException extends RuntimeException {

    private final String username;

    public UsernameExistException(String username) {
        super();
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
