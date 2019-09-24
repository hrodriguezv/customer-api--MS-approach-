package com.client.manager.entities.exception;

public class CompanyMaxNameLengthReachedException extends RuntimeException {
    public CompanyMaxNameLengthReachedException() {
        super(
                CompanyMaxNameLengthReachedException.class.getSimpleName() +
                        ": Company Name length is higher than expected. Company Name max length is 30."
        );
    }
}
