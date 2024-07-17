package com.example.calendarize.exception;

public class ObjAlreadyExistsException extends RuntimeException{
    public ObjAlreadyExistsException(String message) {
        super(message);
    }
}
