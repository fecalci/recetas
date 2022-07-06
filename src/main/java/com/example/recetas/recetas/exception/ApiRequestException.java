package com.example.recetas.recetas.exception;

public class ApiRequestException extends RuntimeException{

    public ApiRequestException(String message){
        super(message);
    }
}
