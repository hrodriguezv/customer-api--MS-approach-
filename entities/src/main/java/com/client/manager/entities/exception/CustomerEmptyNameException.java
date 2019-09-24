package com.client.manager.entities.exception;

public class CustomerEmptyNameException extends RuntimeException {
    public CustomerEmptyNameException() {
        super(
                CustomerEmptyNameException.class.getSimpleName() +
                        ": Customer Name cannot be empty."
        );
    }
}
