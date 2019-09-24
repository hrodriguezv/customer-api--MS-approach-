package com.client.manager.entities.exception;

public class CustomerEmptyPasswordException extends RuntimeException {
    public CustomerEmptyPasswordException() {
        super(
                CustomerEmptyPasswordException.class.getSimpleName() +
                        ": Customer password cannot be empty."
        );
    }
}
