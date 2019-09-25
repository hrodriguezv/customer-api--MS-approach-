package com.client.manager.ws.util;

import com.client.manager.entities.dto.CustomerDTO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityUtil {
    private SecurityUtil() {
    }

    public static String encryptPassword(String password, PasswordEncoder encoder) {
        return encoder.encode(password);
    }

    public static CustomerDTO encryptPassword(CustomerDTO customer, PasswordEncoder encoder) {
        customer.setPassword(SecurityUtil.encryptPassword(customer.getPassword(), encoder));
        return customer;
    }

    public static User buildSecurityUserFrom(com.client.manager.entities.User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                SecurityUtil.convertSimpleGrantedAuthorityFrom(user.getRoles())
        );
    }

    public static List<SimpleGrantedAuthority> convertSimpleGrantedAuthorityFrom(String roles) {
        return Arrays.asList(roles.split(","))
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
