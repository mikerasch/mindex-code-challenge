package com.mindex.challenge.exceptions.handler;

import com.mindex.challenge.exceptions.ResourceNotFoundException;
import com.mindex.challenge.models.exceptions.ExceptionMessage;
import jakarta.annotation.Nonnull;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * All ResourceNotFoundExceptions will be translated to 404 response code.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handleNotFoundException(
            @Nonnull ResourceNotFoundException resourceNotFoundException) {
        return new ResponseEntity<>(
                new ExceptionMessage(resourceNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionMessage> handleValidationExceptions(
            ConstraintViolationException ex) {
        return new ResponseEntity<>(new ExceptionMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
