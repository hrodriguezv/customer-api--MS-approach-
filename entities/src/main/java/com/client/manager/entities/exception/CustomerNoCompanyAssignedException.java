package com.client.manager.entities.exception;

public class CustomerNoCompanyAssignedException extends RuntimeException {
    public CustomerNoCompanyAssignedException() {
        super(
                CustomerNoCompanyAssignedException.class.getSimpleName() +
                        ": There is no company assigned to customer"
        );
    }
}
