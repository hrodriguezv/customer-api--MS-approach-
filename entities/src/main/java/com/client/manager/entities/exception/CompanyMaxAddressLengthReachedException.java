package com.client.manager.entities.exception;

public class CompanyMaxAddressLengthReachedException extends RuntimeException {
    public CompanyMaxAddressLengthReachedException() {
        super(
                CompanyMaxAddressLengthReachedException.class.getSimpleName() +
                        ": Company Address length is higher than expected. Company Address max length is 150."
        );
    }
}
