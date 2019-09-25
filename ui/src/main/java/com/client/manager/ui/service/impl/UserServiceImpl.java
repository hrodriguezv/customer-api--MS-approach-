package com.client.manager.ui.service.impl;

import com.client.manager.entities.dto.UserDTO;
import com.client.manager.ui.service.IUserService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {

    private final OAuth2RestTemplate clientCredentialsRestTemplate;
    private final Environment environment;

    public UserServiceImpl(OAuth2RestTemplate clientCredentialsRestTemplate, Environment environment) {
        this.clientCredentialsRestTemplate = clientCredentialsRestTemplate;
        this.environment = environment;
    }

    @Override
    public ResponseEntity<UserDTO> findByUsername(String username) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString(
                        this.environment.getProperty("ws.user.findbyusername.endpoint")
                );

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("username", String.valueOf(username));

        return this.clientCredentialsRestTemplate.exchange(
                uriComponentsBuilder.buildAndExpand(uriParams).toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<UserDTO>() {
                }
        );
    }
}
