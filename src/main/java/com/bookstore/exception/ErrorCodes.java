package com.bookstore.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodes {

    CART_NOT_FOUND("EC1001", "No such cart for this customer ! "),
    INVALID_QUANTITY_FAILURE("EC1002", "Invalid Quantity "),
    BOOK_NOT_FOUND_FAILURE("EC1003", "failed to get book from DB "),
    OUT_OF_STOCK_FAILURE("EC1004", "No Stock available for desired quantity!"),
    GENERAL_ERROR("EC5xx", "Unexpected error occurred , check stack trace");


    private final String errorCode;
    private final String errorMessage;
}
