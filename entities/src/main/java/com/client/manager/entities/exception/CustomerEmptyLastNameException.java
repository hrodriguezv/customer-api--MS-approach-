package com.client.manager.entities.exception;

public class CustomerEmptyLastNameException extends RuntimeException {
    public CustomerEmptyLastNameException() {
        super(
                CustomerEmptyLastNameException.class.getSimpleName() +
                        ": Customer Last Name cannot be empty."
        );
    }
}
