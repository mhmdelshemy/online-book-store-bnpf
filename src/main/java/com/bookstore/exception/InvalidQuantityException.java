package com.bookstore.exception;

import org.springframework.http.HttpStatus;

public class InvalidQuantityException extends CreatingCartException {

    public InvalidQuantityException() {
        super(HttpStatus.CONFLICT, ErrorCodes.INVALID_QUANTITY_FAILURE.getErrorCode(),
                ErrorCodes.INVALID_QUANTITY_FAILURE.getErrorMessage());
    }
}
