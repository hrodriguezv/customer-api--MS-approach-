package com.client.manager.ws.exception;

import com.client.manager.core.exception.EntityNotFoundException;

public class LoginBadCredentialsException extends EntityNotFoundException {
    public LoginBadCredentialsException() {
        super(
                LoginBadCredentialsException.class.getSimpleName() +
                        ": User credentials incorrect."
        );
    }
}
