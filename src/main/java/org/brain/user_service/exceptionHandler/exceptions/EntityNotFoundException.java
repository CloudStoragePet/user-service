package org.brain.user_service.exceptionHandler.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.brain.user_service.exceptionHandler.model.ErrorType;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class EntityNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private final ErrorType errorType = ErrorType.DATABASE_ERROR;
    private static final String DEFAULT_MESSAGE = "Entity is not found!";

    public EntityNotFoundException() {
        this(DEFAULT_MESSAGE);
    }
    public EntityNotFoundException(String message) {
        super(message);
    }
}
