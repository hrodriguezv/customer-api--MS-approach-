package com.client.manager.entities.exception;

public class CompanyEmptyAddressException extends RuntimeException {
    public CompanyEmptyAddressException() {
        super(
                CompanyEmptyAddressException.class.getSimpleName() +
                        ": Company Address cannot be empty."
        );
    }
}
