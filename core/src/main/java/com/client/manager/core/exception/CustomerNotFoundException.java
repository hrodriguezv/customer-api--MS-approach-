package com.client.manager.core.exception;

public class CustomerNotFoundException extends EntityNotFoundException {
    public CustomerNotFoundException() {
        super("Customer not found. Please make sure you know the customer's id.");
    }
}
