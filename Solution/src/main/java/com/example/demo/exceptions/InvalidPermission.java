package com.example.demo.exceptions;

public class InvalidPermission extends RuntimeException {


    public InvalidPermission(String message, String username, String beerName){
        super(String.format(message, username,beerName));
    }
}
