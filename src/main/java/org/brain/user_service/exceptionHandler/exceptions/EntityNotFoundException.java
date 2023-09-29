package org.brain.user_service.exceptionHandler.exceptions;

import lombok.Getter;
import org.brain.user_service.exceptionHandler.model.ErrorType;

import java.io.Serial;

@Getter
public class EntityNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final ErrorType type = ErrorType.DATABASE_ERROR;
    private static final String DEFAULT_MESSAGE = "Entity is not found!";

    public EntityNotFoundException() {
        this(DEFAULT_MESSAGE);
    }
    public EntityNotFoundException(String message) {
        super(message);
    }
}
