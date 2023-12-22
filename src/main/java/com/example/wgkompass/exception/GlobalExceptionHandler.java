package com.example.wgkompass.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * The GlobalExceptionHandler class provides a centralized exception handling mechanism across the entire WG-Kompass application.
 * It catches exceptions thrown by any controller and provides custom responses.
 * This class is annotated with @RestControllerAdvice, indicating it's a controller advice with response bodies.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles InvalidRequestException by returning an appropriate response.
     * This method is annotated with @ExceptionHandler, indicating it handles exceptions of type InvalidRequestException.
     * The response status for this exception is set to HttpStatus.BAD_REQUEST.
     *
     * @param e the caught InvalidRequestException
     * @return the message from the InvalidRequestException, to be included in the response body
     */
    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidRequestException(InvalidRequestException e) {
        return e.getMessage();
    }
}
