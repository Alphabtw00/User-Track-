package com.example.usertrack.exception;

public class InvalidUpdateFieldsException extends RuntimeException{
    public InvalidUpdateFieldsException(String message){
        super(message);
    }
}
