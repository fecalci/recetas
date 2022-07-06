package com.example.recetas.recetas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value= {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestExceptrion(ApiRequestException e){
        //1.Create payload containing exception details
        ApiException apiException =new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST);
        //2.Return response entitny
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);

    }
}
