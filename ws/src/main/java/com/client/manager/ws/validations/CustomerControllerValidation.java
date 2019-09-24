package com.client.manager.ws.validations;

import com.client.manager.core.exception.DuplicatedCustomerException;
import com.client.manager.entities.Customer;
import com.client.manager.entities.dto.CustomerDTO;
import com.client.manager.entities.dto.validations.CustomerValidation;

import java.util.function.Supplier;

public class CustomerControllerValidation {

    private CustomerControllerValidation() {
    }

    public static void validateDuplicatedOnCreate(Supplier<Long> countRepeatedSupplier) {
        BaseControllerValidation.validateDuplicatedOnCreate(countRepeatedSupplier, DuplicatedCustomerException::new);
    }

    public static void validateDuplicatedOnUpdate(
            CustomerDTO customerToUpdate,
            Customer existingCustomer,
            Supplier<Long> countRepeatedSupplier
    ) {
        if (!existingCustomer.getUsername().equals(customerToUpdate.getUsername()) && countRepeatedSupplier.get() > 0) {
            throw new DuplicatedCustomerException();
        }
    }

    public static void validateCustomer(
            CustomerDTO customerToUpdate,
            Customer existingCustomer,
            Supplier<Long> countRepeatedSupplier
    ) {
        CustomerValidation.validateSkipPasswordLength(customerToUpdate);
        CustomerControllerValidation.validateDuplicatedOnUpdate(customerToUpdate, existingCustomer, countRepeatedSupplier);
    }

    public static void validateCustomer(CustomerDTO customerToUpdate, Supplier<Long> countRepeatedSupplier) {
        CustomerValidation.validate(customerToUpdate);
        CustomerControllerValidation.validateDuplicatedOnCreate(countRepeatedSupplier);
    }
}
