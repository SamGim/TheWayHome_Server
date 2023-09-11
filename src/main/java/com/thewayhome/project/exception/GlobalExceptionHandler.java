package com.thewayhome.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<Object> handleUnknownException(Exception e){
        return new ResponseEntity<>("UnknownException : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleCustomException(CustomException e){
        return new ResponseEntity<>(e.getCustomError().getMessage(), HttpStatusCode.valueOf(e.getCustomError().getStatus()));
    }
}
