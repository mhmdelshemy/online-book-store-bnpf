package com.bookstore.exception;

import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends CreatingCartException {

    public CustomerNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorCodes.CUSTOMER_NOT_FOUND.getErrorCode(),
                ErrorCodes.CUSTOMER_NOT_FOUND.getErrorMessage());
    }
}
