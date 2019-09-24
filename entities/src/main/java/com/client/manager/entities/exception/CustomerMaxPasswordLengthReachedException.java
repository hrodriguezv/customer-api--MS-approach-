package com.client.manager.entities.exception;

public class CustomerMaxPasswordLengthReachedException extends RuntimeException {
    public CustomerMaxPasswordLengthReachedException() {
        super(
                CustomerMaxPasswordLengthReachedException.class.getSimpleName() +
                        ": Customer Password length is higher than expected. Customer Password max length is 30."
        );
    }
}
