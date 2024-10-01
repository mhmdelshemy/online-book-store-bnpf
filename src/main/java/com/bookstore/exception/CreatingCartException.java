package com.bookstore.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class CreatingCartException extends ResponseStatusException {

    final String code;

    public CreatingCartException(HttpStatus httpStatus, String code, String message) {
        super(httpStatus,message);
        this.code = code;
    }
}
