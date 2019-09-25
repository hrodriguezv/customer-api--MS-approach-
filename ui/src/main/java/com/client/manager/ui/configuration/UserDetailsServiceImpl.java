package com.client.manager.ui.configuration;

import com.client.manager.ui.service.IUserService;
import com.client.manager.ui.util.SecurityUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserService userService;

    public UserDetailsServiceImpl(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return Optional.ofNullable(userService.findByUsername(username).getBody())
                .map(SecurityUtil::buildSecurityUserFrom)
                .orElseThrow(() -> new BadCredentialsException("Bad Credentials"));
    }
}
