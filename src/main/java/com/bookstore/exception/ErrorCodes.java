package com.bookstore.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodes {

    CUSTOMER_NOT_FOUND("EC1001", "Failed to get customer from DB "),
    INVALID_QUANTITY_FAILURE("EC1002", "Quantity must be 0 or higher "),
    BOOK_NOT_FOUND_FAILURE("EC1003", "failed to get book from DB "),
    GENERAL_ERROR("EC5xx", "Unexpected error occurred , check stack trace");


    private final String errorCode;
    private final String errorMessage;
}
