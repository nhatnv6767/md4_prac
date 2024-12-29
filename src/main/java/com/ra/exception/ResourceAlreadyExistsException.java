package com.ra.exception;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends CustomException {
    public ResourceAlreadyExistsException(String message) {
        super("RESOURCE_CONFLICT", message, HttpStatus.CONFLICT);
    }
}
