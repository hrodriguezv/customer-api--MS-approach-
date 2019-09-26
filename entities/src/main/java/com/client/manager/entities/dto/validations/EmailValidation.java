package com.client.manager.entities.dto.validations;

import com.client.manager.entities.exception.EmailBadFormatException;
import com.client.manager.entities.exception.EmailMaxLengthException;

import java.util.Optional;
import java.util.regex.Pattern;

public class EmailValidation {

    public static final Long EMAIL_MAX_LENGTH = 200L;

    private EmailValidation() {
    }

    public static void validateEmailLength(String email) {
        Optional.ofNullable(email)
                .filter(s -> s.length() <= EmailValidation.EMAIL_MAX_LENGTH)
                .orElseThrow(EmailMaxLengthException::new);
    }

    public static void validateEmailFormat(String email) {
        Optional.ofNullable(email)
                .filter(EmailValidation::isValid)
                .orElseThrow(EmailBadFormatException::new);
    }

    public static boolean isValid(String email) {
        String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static void validate(String email) {
        EmailValidation.validateEmailLength(email);
        EmailValidation.validateEmailFormat(email);
    }

}
