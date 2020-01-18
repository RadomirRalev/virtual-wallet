package com.example.demo.exceptions;

public class InvalidOptionalFieldParameter extends  RuntimeException {
    public InvalidOptionalFieldParameter(String message) {
        super(message);
    }
}
