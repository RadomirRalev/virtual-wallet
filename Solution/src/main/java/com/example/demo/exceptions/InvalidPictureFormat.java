package com.example.demo.exceptions;

public class InvalidPictureFormat extends RuntimeException {

    public InvalidPictureFormat(String message){
        super(message);
    }
}
