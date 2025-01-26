package com.fripop.product.ws.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@NoArgsConstructor
public class NotFoundException extends Exception implements Serializable {

    public static final HttpStatus HTTP_NOT_FOUND = HttpStatus.NOT_FOUND;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

}
