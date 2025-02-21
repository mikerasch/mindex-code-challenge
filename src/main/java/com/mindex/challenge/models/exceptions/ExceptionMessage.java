package com.mindex.challenge.models.exceptions;

import java.time.LocalDateTime;

public record ExceptionMessage(String message, LocalDateTime time) {
    public ExceptionMessage(String message) {
        this(message, LocalDateTime.now());
    }
}
