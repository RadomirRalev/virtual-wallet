package com.example.demo.exceptions;

public class InvalidWalletException extends RuntimeException {
    public InvalidWalletException(String message) {
        super(message);
    }
}
