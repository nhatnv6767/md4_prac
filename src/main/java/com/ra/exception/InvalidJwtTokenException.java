package com.ra.exception;

import org.springframework.http.HttpStatus;

public class InvalidJwtTokenException extends CustomException {
    public InvalidJwtTokenException(String message) {
        super("INVALID_JWT_TOKEN", message, HttpStatus.UNAUTHORIZED);
    }
}
