package com.client.manager.ws.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException() {
        super("Customer not found. Please make sure you know the customer's id.");
    }
}
