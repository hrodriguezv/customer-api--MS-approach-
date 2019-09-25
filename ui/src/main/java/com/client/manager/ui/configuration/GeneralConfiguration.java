package com.client.manager.ui.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EnableOAuth2Client
@Configuration
public class GeneralConfiguration {

    @Value("${security.oauth2.token.endpoint}")
    private String tokenUrl;

    @Value("${security.oauth2.client.id}")
    private String clientId;

    @Value("${security.oauth2.client.secret}")
    private String clientSecret;

    @Bean
    protected ResourceOwnerPasswordResourceDetails oauth2UserPasswordResource() {

        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();

        List<String> scopes = new ArrayList<>();
        scopes.add("write");
        scopes.add("read");
        resource.setAccessTokenUri(tokenUrl);
        resource.setClientId(clientId);
        resource.setClientSecret(clientSecret);
        resource.setScope(scopes);

        return resource;
    }

    @Bean
    protected ClientCredentialsResourceDetails oauth2ClientCredentialsResource() {

        ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();

        List<String> scopes = new ArrayList<>();
        scopes.add("write");
        scopes.add("read");
        resource.setAccessTokenUri(tokenUrl);
        resource.setClientId(clientId);
        resource.setClientSecret(clientSecret);
        resource.setScope(scopes);

        return resource;
    }

    @Bean
    public OAuth2RestTemplate clientCredentialsRestTemplate() {
        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(oauth2ClientCredentialsResource(), new DefaultOAuth2ClientContext(atr));

        oAuth2RestTemplate.setErrorHandler(responseErrorHandler());

        return oAuth2RestTemplate;
    }

    @Bean
    public OAuth2RestTemplate userPasswordRestTemplate() {
        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(oauth2UserPasswordResource(), new DefaultOAuth2ClientContext(atr));

        oAuth2RestTemplate.setErrorHandler(responseErrorHandler());

        return oAuth2RestTemplate;
    }

    private ResponseErrorHandler responseErrorHandler() {
        return new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
                return clientHttpResponse.getStatusCode().isError();
            }

            @Override
            public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
                String message;
                try (BufferedReader br = new BufferedReader(new InputStreamReader(clientHttpResponse.getBody()))) {
                    message = br.lines().collect(Collectors.joining(System.lineSeparator()));
                }
                throw new RuntimeException(message);
            }
        };
    }
}