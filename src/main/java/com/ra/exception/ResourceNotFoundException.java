package com.ra.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends CustomException {
    public ResourceNotFoundException(String message) {
        super("RESOURCE_NOT_FOUND", message, HttpStatus.NOT_FOUND);
    }
}
