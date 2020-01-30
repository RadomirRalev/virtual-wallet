package com.example.demo.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class DuplicateIdempotencyKeyException extends DataIntegrityViolationException {

    public DuplicateIdempotencyKeyException(String message) {
        super(message);
    }
}
