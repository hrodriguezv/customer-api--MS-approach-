package com.client.manager.entities.exception;

public class CompanyEmptyNameException extends RuntimeException {
    public CompanyEmptyNameException() {
        super(
                CompanyEmptyNameException.class.getSimpleName() +
                        ": Company Name cannot be empty."
        );
    }
}
