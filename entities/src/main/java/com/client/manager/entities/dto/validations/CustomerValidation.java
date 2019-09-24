package com.client.manager.entities.dto.validations;

import com.client.manager.entities.dto.CompanyDTO;
import com.client.manager.entities.dto.CustomerDTO;
import com.client.manager.entities.exception.*;

import java.util.Optional;

public class CustomerValidation {

    public static final Long NAME_MAX_LENGTH = 30L;
    public static final Long LAST_NAME_MAX_LENGTH = 30L;
    public static final Long USERNAME_MAX_LENGTH = 30L;
    public static final Long PASSWORD_MAX_LENGTH = 30L;
    public static final Long ADDRESS_MAX_LENGTH = 150L;

    private CustomerValidation() {
    }

    public static void validateNameEmpty(CustomerDTO customer) {
        Optional.ofNullable(customer.getName())
                .map(s -> !s.isEmpty())
                .orElseThrow(CustomerEmptyNameException::new);
    }

    public static void validateNameLength(CustomerDTO customer) {
        Optional.ofNullable(customer.getName())
                .map(s -> s.length() <= CustomerValidation.NAME_MAX_LENGTH)
                .orElseThrow(CustomerMaxNameLengthReachedException::new);
    }

    public static void validateLastNameEmpty(CustomerDTO customer) {
        Optional.ofNullable(customer.getLastName())
                .map(s -> !s.isEmpty())
                .orElseThrow(CustomerEmptyLastNameException::new);
    }

    public static void validateLastNameLength(CustomerDTO customer) {
        Optional.ofNullable(customer.getLastName())
                .map(s -> s.length() <= CustomerValidation.LAST_NAME_MAX_LENGTH)
                .orElseThrow(CustomerMaxLastNameLengthReachedException::new);
    }

    public static void validateUsernameEmpty(CustomerDTO customer) {
        Optional.ofNullable(customer.getUsername())
                .map(s -> !s.isEmpty())
                .orElseThrow(CustomerEmptyUsernameException::new);
    }

    public static void validateUsernameLength(CustomerDTO customer) {
        Optional.ofNullable(customer.getUsername())
                .map(s -> s.length() <= CustomerValidation.USERNAME_MAX_LENGTH)
                .orElseThrow(CustomerMaxUsernameLengthReachedException::new);
    }

    public static void validatePasswordEmpty(CustomerDTO customer) {
        Optional.ofNullable(customer.getPassword())
                .map(s -> !s.isEmpty())
                .orElseThrow(CustomerEmptyPasswordException::new);
    }

    public static void validatePasswordLength(CustomerDTO customer) {
        Optional.ofNullable(customer.getPassword())
                .map(s -> s.length() <= CustomerValidation.PASSWORD_MAX_LENGTH)
                .orElseThrow(CustomerMaxPasswordLengthReachedException::new);
    }

    public static void validateCompany(CustomerDTO customer) {
        Optional.ofNullable(customer.getCompany())
                .map(CompanyDTO::getId)
                .orElseThrow(CustomerNoCompanyAssignedException::new);
    }

    public static void validateAddressLength(CustomerDTO customer) {
        Optional.ofNullable(customer.getAddress())
                .map(s -> s.length() <= CustomerValidation.ADDRESS_MAX_LENGTH)
                .orElseThrow(CustomerMaxAddressLengthReachedException::new);
    }

    public static void validate(CustomerDTO customer) {
        CustomerValidation.validateNameEmpty(customer);
        CustomerValidation.validateNameLength(customer);
        CustomerValidation.validateLastNameEmpty(customer);
        CustomerValidation.validateLastNameLength(customer);
        CustomerValidation.validateUsernameEmpty(customer);
        CustomerValidation.validateUsernameLength(customer);
        CustomerValidation.validatePasswordEmpty(customer);
        CustomerValidation.validatePasswordLength(customer);
        CustomerValidation.validateCompany(customer);
        CustomerValidation.validateAddressLength(customer);
        EmailValidation.validate(customer.getEmail());
    }

}
