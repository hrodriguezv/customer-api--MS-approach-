package com.client.manager.entities.exception;

public class CustomerEmptyUsernameException extends RuntimeException {
    public CustomerEmptyUsernameException() {
        super(
                CustomerEmptyUsernameException.class.getSimpleName() +
                        ": Customer username cannot be empty."
        );
    }
}
