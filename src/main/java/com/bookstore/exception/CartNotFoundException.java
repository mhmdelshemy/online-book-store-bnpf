package com.bookstore.exception;

import org.springframework.http.HttpStatus;

public class CartNotFoundException extends CreatingCartException {

    public CartNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorCodes.CART_NOT_FOUND.getErrorCode(),
                ErrorCodes.CART_NOT_FOUND.getErrorMessage());
    }
}
