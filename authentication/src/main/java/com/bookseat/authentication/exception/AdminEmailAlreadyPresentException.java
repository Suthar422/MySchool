package com.bookseat.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AdminEmailAlreadyPresentException extends RuntimeException {
    public AdminEmailAlreadyPresentException(String message) {
        super(message);
    }
}
