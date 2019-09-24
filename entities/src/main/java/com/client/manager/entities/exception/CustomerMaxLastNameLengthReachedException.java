package com.client.manager.entities.exception;

public class CustomerMaxLastNameLengthReachedException extends RuntimeException {
    public CustomerMaxLastNameLengthReachedException() {
        super(
                CustomerMaxLastNameLengthReachedException.class.getSimpleName() +
                        ": Customer Last Name length is higher than expected. Customer Last Name max length is 30."
        );
    }
}
