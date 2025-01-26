package com.fripop.product.ws.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class Error {
    private String status;
    private String code;
    private String message;

    public Error(HttpStatus status, String message) {
        if (status == null) {
            status = HttpStatus.BAD_REQUEST;
        }

        this.code = String.valueOf(status.value());
        this.status = status.getReasonPhrase();
        this.message = message;
    }
}