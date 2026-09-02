package com.bookseat.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SchoolAlreadyExistsException extends RuntimeException {
    public SchoolAlreadyExistsException(String message) {
        super(message);
    }
}
