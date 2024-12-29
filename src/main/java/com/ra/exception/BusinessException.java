package com.ra.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends CustomException {
    public BusinessException(String message) {
        super("BAD_REQUEST", message, HttpStatus.BAD_REQUEST);
    }
}
