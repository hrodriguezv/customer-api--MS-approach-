package com.client.manager.core.exception;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException() {
        super(
                UserNotFoundException.class.getSimpleName() +
                        ": User not found. Please make sure you know the user's username."
        );
    }
}
