package com.onseotestapp.exceptions;

public class StatusNotFoundException extends RuntimeException{
    public StatusNotFoundException(Long id){
        super(String.format("Status with Id %d not found", id));
    }
}
