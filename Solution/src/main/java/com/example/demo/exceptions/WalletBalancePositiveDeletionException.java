package com.example.demo.exceptions;

public class WalletBalancePositiveDeletionException extends RuntimeException {

    public WalletBalancePositiveDeletionException(String message) {
        super(message);
    }
}
