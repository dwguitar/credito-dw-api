package com.dw.credito.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CreditoNotFoundException extends RuntimeException {
    public CreditoNotFoundException(String message) {
        super(message);
    }
}

