package com.client.manager.ws.configuration;

import com.client.manager.core.exception.UserNotFoundException;
import com.client.manager.core.service.IUserService;
import com.client.manager.ws.security.SecurityUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserService userService;

    public UserDetailsServiceImpl(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userService.findByUsername(username)
                .map(SecurityUtil::buildSecurityUserFrom)
                .orElseThrow(UserNotFoundException::new);
    }
}
