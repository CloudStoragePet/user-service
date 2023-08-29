package org.brain.user_service.exceptionHandler.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.brain.user_service.exceptionHandler.model.ErrorType;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServiceException extends Exception {
    private static final long serialVersionUID = 1L;

    private ErrorType errorType = ErrorType.FATAL_ERROR;

    ServiceException(String message) {
        super(message);
    }

}
