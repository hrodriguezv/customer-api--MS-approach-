package com.client.manager.ui.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Configuration
public class GeneralConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setErrorHandler(
                new ResponseErrorHandler() {
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
                }
        );

        return restTemplate;
    }
}