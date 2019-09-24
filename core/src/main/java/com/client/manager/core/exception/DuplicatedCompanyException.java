package com.client.manager.core.exception;

public class DuplicatedCompanyException extends RuntimeException {
    public DuplicatedCompanyException() {
        super(
                DuplicatedCompanyException.class.getSimpleName() +
                        ": Company already exists."
        );
    }
}
