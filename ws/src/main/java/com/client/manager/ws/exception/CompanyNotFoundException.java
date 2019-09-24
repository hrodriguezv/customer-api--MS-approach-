package com.client.manager.ws.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CompanyNotFoundException extends Exception {
    public CompanyNotFoundException() {
        super("Company not found. Please make sure you know the company's id.");
    }
}
