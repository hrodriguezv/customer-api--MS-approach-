package com.client.manager.entities.exception;

public class EmailBadFormatException extends RuntimeException {
    public EmailBadFormatException() {
        super(
                EmailBadFormatException.class.getSimpleName() +
                        ": Email format incorrect."
        );
    }
}
