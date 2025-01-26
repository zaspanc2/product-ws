package com.fripop.product.ws.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

/**
 * Global exception handles.
 * <p>
 * Used for intercepting and mapping exceptions.
 *
 * @since 1.0.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles mappings for general {@link Exception}.
     *
     * @param exception {@link Exception}
     * @return {@link ResponseEntity} with {@link Error} and {@link HttpStatus#INTERNAL_SERVER_ERROR}
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {

        // Set generic message for any unhandled exception to prevent disclosing any unwanted information.
        return new ResponseEntity<>(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles mappings for specific {@link NotFoundException}.
     *
     * @param notFoundException {@link NotFoundException}
     * @return {@link ResponseEntity} with {@link Error} and {@link HttpStatus#NOT_FOUND}
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException notFoundException) {
        return new ResponseEntity<>(new Error(HttpStatus.NOT_FOUND, notFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles mappings for specific {@link BadRequestException}.
     *
     * @param badRequestException {@link BadRequestException}
     * @return {@link ResponseEntity} with {@link Error} and {@link HttpStatus#BAD_REQUEST}
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException badRequestException) {
        return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST, badRequestException.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles mappings for specific {@link BindException}.
     * <p>
     * This exception is thrown when one or more fields are invalid.
     *
     * @param bindException {@link BindException}
     * @return {@link ResponseEntity} with {@link Error} and {@link HttpStatus#BAD_REQUEST}
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBindException(BindException bindException) {

        var message = "Bad request";

        // Set message of the first bind error as a response if it exists.
        var firstValidationOpt = bindException.getBindingResult().getFieldErrors().stream().findFirst();
        if (firstValidationOpt.isPresent()) {
            message = firstValidationOpt.get().getDefaultMessage();
        }

        return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST, message), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles mappings for generic validation related {@link Exception}s.
     *
     * @param validationException {@link Exception}
     * @return {@link ResponseEntity} with {@link Error} and {@link HttpStatus#BAD_REQUEST}
     */
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            HandlerMethodValidationException.class,
            MissingServletRequestParameterException.class
    })
    public ResponseEntity<?> handleValidationException(Exception validationException) {
        return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST, validationException.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
