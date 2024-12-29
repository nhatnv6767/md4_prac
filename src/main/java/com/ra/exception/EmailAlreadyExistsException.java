package com.ra.exception;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends CustomException {
    public EmailAlreadyExistsException(String message) {
        super("EMAIL_ALREADY_EXISTS", message != null ? message : "Email already exists", HttpStatus.CONFLICT);
    }

//    public EmailAlreadyExistsException() {
//        this("Email already exists");
//    }
}
