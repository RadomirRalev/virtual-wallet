package com.example.demo.exceptions;

public class InvalidPermission extends RuntimeException {


    public InvalidPermission(String message, String username){
        super(String.format(message, username));
    }
}
