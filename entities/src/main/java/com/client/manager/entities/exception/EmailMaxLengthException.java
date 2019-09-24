package com.client.manager.entities.exception;

public class EmailMaxLengthException extends RuntimeException {
    public EmailMaxLengthException() {
        super(
                EmailMaxLengthException.class.getSimpleName() +
                        ": Email length is higher than expected.Email max length is 200."
        );
    }
}
