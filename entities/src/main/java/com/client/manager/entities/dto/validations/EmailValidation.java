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
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static void validate(String email) {
        Optional.ofNullable(email).ifPresent((e) -> {
            EmailValidation.validateEmailLength(e);
            EmailValidation.validateEmailFormat(e);
        });
    }

}
