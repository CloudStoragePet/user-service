package org.brain.user_service.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.brain.user_service.exceptionHandler.exceptions.EntityNotFoundException;
import org.brain.user_service.exceptionHandler.exceptions.ServiceException;
import org.brain.user_service.exceptionHandler.model.Error;
import org.brain.user_service.exceptionHandler.model.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Error>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("handleMethodArgumentNotValidException: message {}", ex.getMessage(), ex);
        List<Error> errorList = ex.getBindingResult().getAllErrors().stream()
                .map(err -> new Error(err.getDefaultMessage(), ErrorType.VALIDATION_ERROR, LocalDateTime.now()))
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errorList);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Error> handleServiceException(ServiceException ex, HandlerMethod hm) {
        log.error("handleServiceException: message {}, method {}", ex.getMessage(), hm.getMethod().getName(), ex);
        Error error = new Error(ex.getMessage(), ex.getErrorType(), LocalDateTime.now());
        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Error> handleEntityNotFoundException(EntityNotFoundException ex, HandlerMethod hm) {
        log.error("handleEntityNotFoundException: message {}, method {}", ex.getMessage(), hm.getMethod().getName(), ex);
        Error error = new Error(ex.getMessage(), ex.getErrorType(), LocalDateTime.now());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Error> handleBadCredentialsException(Exception ex, HandlerMethod hm) {
        log.error("handleBadCredentialsException: message {}, method {}", ex.getMessage(), hm.getMethod().getName(), ex);
        Error error = new Error(ex.getMessage(), ErrorType.PROCESSING_ERROR, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Error> handleAuthenticationException(Exception ex, HandlerMethod hm) {
        log.error("handleAuthenticationException: message {}, method {}", ex.getMessage(), hm.getMethod().getName(), ex);
        Error error = new Error(ex.getMessage(), ErrorType.PROCESSING_ERROR, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(Exception ex, HandlerMethod hm) {
        log.error("handleException: message {}, method {}", ex.getMessage(), hm.getMethod().getName(), ex);
        Error error = new Error(ex.getMessage(), ErrorType.FATAL_ERROR, LocalDateTime.now());
        return ResponseEntity.internalServerError().body(error);
    }

}
