package com.client.manager.core.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super("Entity not found. Please make sure you know the entity's id.");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
