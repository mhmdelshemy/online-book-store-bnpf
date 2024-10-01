package com.bookstore.exception.handler;

import com.bookstore.exception.CreatingCartException;
import com.bookstore.exception.CreatingCartFailure;
import com.bookstore.exception.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({CreatingCartException.class})
    public ResponseEntity<CreatingCartFailure> handleApiException(CreatingCartException ex)
    {
        var failure = new CreatingCartFailure();
        failure.setCode(ex.getCode());
        failure.setMessage(ex.getMessage());
        ex.printStackTrace();

        return new ResponseEntity<>(failure, ex.getStatusCode());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<CreatingCartFailure> handleGlobalException(Exception ex)
    {
        var failure = new CreatingCartFailure();
        failure.setCode(ErrorCodes.GENERAL_ERROR.getErrorCode());
        failure.setMessage(ex.getMessage());
        ex.printStackTrace();

        return new ResponseEntity<>(failure, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
