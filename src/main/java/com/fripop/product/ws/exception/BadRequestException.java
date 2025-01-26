package com.fripop.product.ws.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * Bad request exception with {@link HttpStatus#BAD_REQUEST}.
 *
 * @since 1.0.0
 */
@NoArgsConstructor
public class BadRequestException extends Exception implements Serializable {

    public static final HttpStatus HTTP_BAD_REQUEST = HttpStatus.BAD_REQUEST;

    public BadRequestException(String message) {
        super(message);
    }
}
