package com.demo.exception;

public class InvalidCSVException extends RuntimeException {
    public InvalidCSVException(String message) {
        super(message);
    }
}
