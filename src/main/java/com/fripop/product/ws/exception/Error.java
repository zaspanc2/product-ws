package com.fripop.product.ws.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Exception error model.
 * <p>
 * This model is returned as a response when any exception occurs.
 *
 * @since 1.0.0
 */
@Getter
@Setter
public class Error {
    private String status;
    private String code;
    private String message;

    public Error(HttpStatus status, String message) {
        if (status == null) {

            // If status is not provided, set it to 500.
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        this.code = String.valueOf(status.value());
        this.status = status.getReasonPhrase();
        this.message = message;
    }
}