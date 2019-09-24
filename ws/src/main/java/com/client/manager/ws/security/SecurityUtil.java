package com.client.manager.ws.security;

import com.client.manager.entities.dto.CustomerDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityUtil {
    private SecurityUtil() {
    }

    public static String encryptPassword(String password, PasswordEncoder encoder) {
        return encoder.encode(password);
    }

    public static CustomerDTO encryptPassword(CustomerDTO customer) {
        customer.setPassword(SecurityUtil.encryptPassword(customer.getPassword(), new BCryptPasswordEncoder()));
        return customer;
    }
}
