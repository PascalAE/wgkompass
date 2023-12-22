package com.example.wgkompass.exception;

/**
 * The InvalidRequestException class represents a custom exception thrown when an invalid request
 * occurs within the context of the WG-Kompass application. It extends RuntimeException,
 * allowing runtime responses to specific request errors.
 */
public class InvalidRequestException extends RuntimeException {

    /**
     * Constructs a new InvalidRequestException with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the getMessage() method.
     */
    public InvalidRequestException(String message) {
        super(message);
    }
}
