package com.client.manager.entities.dto.validations;

import com.client.manager.entities.dto.CompanyDTO;
import com.client.manager.entities.exception.CompanyEmptyAddressException;
import com.client.manager.entities.exception.CompanyEmptyNameException;
import com.client.manager.entities.exception.CompanyMaxAddressLengthReachedException;
import com.client.manager.entities.exception.CompanyMaxNameLengthReachedException;

import java.util.Optional;

public class CompanyValidation {

    public static final Long NAME_MAX_LENGTH = 30L;
    public static final Long ADDRESS_MAX_LENGTH = 150L;

    private CompanyValidation() {
    }

    public static void validateNameEmpty(CompanyDTO company) {
        Optional.ofNullable(company.getName())
                .filter(s -> !s.isEmpty())
                .orElseThrow(CompanyEmptyNameException::new);
    }

    public static void validateNameLength(CompanyDTO company) {
        Optional.ofNullable(company.getName())
                .filter(s -> s.length() <= CompanyValidation.NAME_MAX_LENGTH)
                .orElseThrow(CompanyMaxNameLengthReachedException::new);
    }

    public static void validateAddressEmpty(CompanyDTO company) {
        Optional.ofNullable(company.getAddress())
                .filter(s -> !s.isEmpty())
                .orElseThrow(CompanyEmptyAddressException::new);
    }

    public static void validateAddressLength(CompanyDTO company) {
        Optional.ofNullable(company.getAddress())
                .filter(s -> s.length() <= CompanyValidation.ADDRESS_MAX_LENGTH)
                .orElseThrow(CompanyMaxAddressLengthReachedException::new);
    }

    public static void validate(CompanyDTO company) {
        CompanyValidation.validateNameEmpty(company);
        CompanyValidation.validateNameLength(company);
        CompanyValidation.validateAddressEmpty(company);
        CompanyValidation.validateAddressLength(company);
    }

}
