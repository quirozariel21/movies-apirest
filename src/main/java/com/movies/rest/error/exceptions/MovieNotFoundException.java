package com.movies.rest.error.exceptions;

public class MovieNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 43876691117560211L;

    public MovieNotFoundException() {
        super("Movies not found.");
    }

    public MovieNotFoundException(Long id) {
        super("Cannot found movie with ID: " + id);
    }

    public MovieNotFoundException(String message) {
        super(message);
    }
}
