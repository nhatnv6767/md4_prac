package com.ra.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private String errorCode;
    private HttpStatus status;

    public CustomException(String errorCode, String message, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}
