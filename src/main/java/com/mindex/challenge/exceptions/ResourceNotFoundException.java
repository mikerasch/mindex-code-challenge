package com.mindex.challenge.exceptions;

/**
 * Used to represent a 404 exception.
 * By throwing this, a global exception handler will convert the exception
 * into a 404 response with a String message.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
