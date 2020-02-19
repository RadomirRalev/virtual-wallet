package com.example.demo.exceptions;

public class DefaultWalletDeletionException extends RuntimeException {

    public DefaultWalletDeletionException(String message) {
        super(message);
    }

    public DefaultWalletDeletionException(String message, int id) {
        super(String.format(message, id));
    }
}
