package com.example.studentapp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// @RestControllerAdvice: this class handles exceptions thrown by ANY controller
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Runs when @Valid finds invalid data
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Collect one message per invalid field: field name -> message
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        // 400 Bad Request: the client sent wrong data
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
