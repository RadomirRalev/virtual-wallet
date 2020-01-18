package com.example.demo.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, String name) {
        super(String.format(message, name));
    }

    public EntityNotFoundException(String message, int id) {
        super(String.format(message, id));
    }
}

