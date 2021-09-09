package com.demo.controller;

import com.demo.exception.InvalidCSVException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

// Controller exception handler for all
@RestControllerAdvice
public class ControllerAdvice {


    @ExceptionHandler(IOException.class)
    public ResponseEntity<Void> catchIOException(final IOException io) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    @ExceptionHandler(InvalidCSVException.class)
    public ResponseEntity<Object> catchInvalidCSVException(final InvalidCSVException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleException(final Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
