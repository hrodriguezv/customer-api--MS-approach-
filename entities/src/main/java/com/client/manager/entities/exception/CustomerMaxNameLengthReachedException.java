package com.client.manager.entities.exception;

public class CustomerMaxNameLengthReachedException extends RuntimeException {
    public CustomerMaxNameLengthReachedException() {
        super(
                CustomerMaxNameLengthReachedException.class.getSimpleName() +
                        ": Customer Name length is higher than expected. Customer Name max length is 30."
        );
    }
}
