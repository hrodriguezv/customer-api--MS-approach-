package com.client.manager.entities.exception;

public class CustomerMaxAddressLengthReachedException extends RuntimeException {
    public CustomerMaxAddressLengthReachedException() {
        super(
                CustomerMaxAddressLengthReachedException.class.getSimpleName() +
                        ": Customer Address length is higher than expected. Customer Address max length is 150."
        );
    }
}
