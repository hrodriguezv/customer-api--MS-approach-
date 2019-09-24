package com.client.manager.core.exception;

public class DuplicatedCustomerException extends RuntimeException {
    public DuplicatedCustomerException() {
        super(
                DuplicatedCustomerException.class.getSimpleName() +
                        ": Customer already exists."
        );
    }
}
