package com.client.manager.core.validations;

import com.client.manager.entities.dto.CompanyDTO;
import com.client.manager.entities.dto.CustomerDTO;
import com.client.manager.entities.exception.*;

import java.util.Optional;

public class CustomerServiceValidation {

    public static final Long NAME_MAX_LENGTH = 30L;
    public static final Long LAST_NAME_MAX_LENGTH = 30L;
    public static final Long USERNAME_MAX_LENGTH = 30L;
    public static final Long PASSWORD_MAX_LENGTH = 30L;
    public static final Long ADDRESS_MAX_LENGTH = 150L;

    private CustomerServiceValidation() {
    }

    public static void validateNameEmpty(CustomerDTO customer) {
        Optional.ofNullable(customer.getName())
                .map(s -> !s.isEmpty())
                .orElseThrow(CustomerEmptyNameException::new);
    }

    public static void validateNameLength(CustomerDTO customer) {
        Optional.ofNullable(customer.getName())
                .map(s -> s.length() <= CustomerServiceValidation.NAME_MAX_LENGTH)
                .orElseThrow(CustomerMaxNameLengthReachedException::new);
    }

    public static void validateLastNameEmpty(CustomerDTO customer) {
        Optional.ofNullable(customer.getLastName())
                .map(s -> !s.isEmpty())
                .orElseThrow(CustomerEmptyLastNameException::new);
    }

    public static void validateLastNameLength(CustomerDTO customer) {
        Optional.ofNullable(customer.getLastName())
                .map(s -> s.length() <= CustomerServiceValidation.LAST_NAME_MAX_LENGTH)
                .orElseThrow(CustomerMaxLastNameLengthReachedException::new);
    }

    public static void validateUsernameEmpty(CustomerDTO customer) {
        Optional.ofNullable(customer.getUsername())
                .map(s -> !s.isEmpty())
                .orElseThrow(CustomerEmptyUsernameException::new);
    }

    public static void validateUsernameLength(CustomerDTO customer) {
        Optional.ofNullable(customer.getUsername())
                .map(s -> s.length() <= CustomerServiceValidation.USERNAME_MAX_LENGTH)
                .orElseThrow(CustomerMaxUsernameLengthReachedException::new);
    }

    public static void validatePasswordEmpty(CustomerDTO customer) {
        Optional.ofNullable(customer.getPassword())
                .map(s -> !s.isEmpty())
                .orElseThrow(CustomerEmptyPasswordException::new);
    }

    public static void validatePasswordLength(CustomerDTO customer) {
        Optional.ofNullable(customer.getPassword())
                .map(s -> s.length() <= CustomerServiceValidation.PASSWORD_MAX_LENGTH)
                .orElseThrow(CustomerMaxPasswordLengthReachedException::new);
    }

    public static void validateCompany(CustomerDTO customer) {
        Optional.ofNullable(customer.getCompany())
                .map(CompanyDTO::getId)
                .orElseThrow(CustomerNoCompanyAssignedException::new);
    }

    public static void validateAddressLength(CustomerDTO customer) {
        Optional.ofNullable(customer.getAddress())
                .map(s -> s.length() <= CustomerServiceValidation.ADDRESS_MAX_LENGTH)
                .orElseThrow(CustomerMaxAddressLengthReachedException::new);
    }

    public static void validate(CustomerDTO customer) {
        CustomerServiceValidation.validateNameEmpty(customer);
        CustomerServiceValidation.validateNameLength(customer);
        CustomerServiceValidation.validateLastNameEmpty(customer);
        CustomerServiceValidation.validateLastNameLength(customer);
        CustomerServiceValidation.validateUsernameEmpty(customer);
        CustomerServiceValidation.validateUsernameLength(customer);
        CustomerServiceValidation.validatePasswordEmpty(customer);
        CustomerServiceValidation.validatePasswordLength(customer);
        CustomerServiceValidation.validateCompany(customer);
        CustomerServiceValidation.validateAddressLength(customer);
        EmailValidation.validate(customer.getEmail());
    }

}
