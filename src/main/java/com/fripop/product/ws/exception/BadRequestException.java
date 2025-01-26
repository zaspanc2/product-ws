package com.fripop.product.ws.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@NoArgsConstructor
public class BadRequestException extends Exception implements Serializable {

    public static final HttpStatus HTTP_NOT_FOUND = HttpStatus.BAD_REQUEST;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

}
