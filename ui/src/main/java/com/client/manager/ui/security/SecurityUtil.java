package com.client.manager.ui.security;

import com.client.manager.entities.dto.UserDTO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityUtil {
    private SecurityUtil() {
    }

    public static User buildSecurityUserFrom(UserDTO user) {
        return new User(
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
