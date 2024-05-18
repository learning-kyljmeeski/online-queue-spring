package com.kyljmeeski.onlinequeue.exception.advice;

import com.kyljmeeski.onlinequeue.exception.UsernameIsTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler(UsernameIsTakenException.class)
    public ResponseEntity<Object> handlePropertyValueException(UsernameIsTakenException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }
}
