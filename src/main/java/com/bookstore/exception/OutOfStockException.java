package com.bookstore.exception;

import org.springframework.http.HttpStatus;

public class OutOfStockException extends CreatingCartException {

    public OutOfStockException() {
        super(HttpStatus.CONFLICT, ErrorCodes.OUT_OF_STOCK_FAILURE.getErrorCode(),
                ErrorCodes.OUT_OF_STOCK_FAILURE.getErrorMessage());
    }
}
