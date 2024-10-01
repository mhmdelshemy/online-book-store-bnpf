package com.bookstore.exception;

import org.springframework.http.HttpStatus;

public class BookNotFoundException extends CreatingCartException {

    public BookNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorCodes.BOOK_NOT_FOUND_FAILURE.getErrorCode(),
                ErrorCodes.BOOK_NOT_FOUND_FAILURE.getErrorMessage());
    }
}
