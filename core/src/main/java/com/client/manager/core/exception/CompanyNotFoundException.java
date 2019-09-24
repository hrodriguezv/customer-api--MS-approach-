package com.client.manager.core.exception;

public class CompanyNotFoundException extends EntityNotFoundException {
    public CompanyNotFoundException() {
        super("Company not found. Please make sure you know the company's id.");
    }
}
