package com.movies.rest.Upload;

public class StorageException extends RuntimeException {

    private static final long serialVersionUID = -5502351264978098291L;

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

}