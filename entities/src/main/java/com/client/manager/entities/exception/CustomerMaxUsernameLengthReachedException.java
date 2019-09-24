package com.client.manager.entities.exception;

public class CustomerMaxUsernameLengthReachedException extends RuntimeException {
    public CustomerMaxUsernameLengthReachedException() {
        super(
                CustomerMaxUsernameLengthReachedException.class.getSimpleName() +
                        ": Customer Username length is higher than expected. Customer Username max length is 30."
        );
    }
}
