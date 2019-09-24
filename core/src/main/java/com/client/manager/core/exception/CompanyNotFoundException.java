package com.client.manager.core.exception;

public class CompanyNotFoundException extends EntityNotFoundException {
    public CompanyNotFoundException() {
        super(
                CompanyNotFoundException.class.getSimpleName() +
                        ": Company not found. Please make sure you know the company's id."
        );
    }
}
