package com.movies.rest.error.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User not found.");
    }

    public UserNotFoundException(Long id) {
        super("Cannot found user with ID: " + id);
    }
}
