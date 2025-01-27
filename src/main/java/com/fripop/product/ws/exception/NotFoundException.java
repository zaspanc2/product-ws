package com.fripop.product.ws.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * Not found exception with {@link HttpStatus#NOT_FOUND}.
 *
 * @since 1.0.0
 */
@NoArgsConstructor
public class NotFoundException extends Exception implements Serializable {

    public static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public NotFoundException(String message) {
        super(message);
    }
}
