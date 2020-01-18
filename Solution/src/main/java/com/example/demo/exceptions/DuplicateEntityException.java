package com.example.demo.exceptions;

public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException(String message) {
        super(message);
    }

    public DuplicateEntityException(String message, String name) {
        super(String.format(message, name));
    }
}
