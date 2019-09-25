package com.client.manager.ui.service.impl;

import com.client.manager.entities.dto.CustomerDTO;
import com.client.manager.entities.dto.PageDTO;
import com.client.manager.ui.service.ICustomerService;
import com.client.manager.ui.util.UIUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final OAuth2RestTemplate userPasswordRestTemplate;
    private final Environment environment;

    public CustomerServiceImpl(OAuth2RestTemplate userPasswordRestTemplate, Environment environment) {
        this.userPasswordRestTemplate = userPasswordRestTemplate;
        this.environment = environment;
    }

    @Override
    public ResponseEntity<CustomerDTO> getById(Long customerId) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString(
                        this.environment.getProperty("ws.customer.findbyid.endpoint")
                );

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("customerId", String.valueOf(customerId));

        return this.userPasswordRestTemplate.exchange(
                uriComponentsBuilder.buildAndExpand(uriParams).toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<CustomerDTO>() {
                }
        );
    }

    @Override
    public ResponseEntity<PageDTO<CustomerDTO>> getList(
            Long companyId,
            String status,
            String criteria,
            Pageable pageable
    ) {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString(
                        this.environment.getProperty("ws.customer.find.endpoint")
                )
                .queryParam("companyId", companyId)
                .queryParam("status", status)
                .queryParam("criteria", criteria);

        Optional.ofNullable(pageable)
                .ifPresent(p -> UIUtil.setPageableParameters(p, uriComponentsBuilder));

        return this.userPasswordRestTemplate.exchange(
                uriComponentsBuilder.build().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDTO<CustomerDTO>>() {
                }
        );
    }

    @Override
    public ResponseEntity save(CustomerDTO customerDTO) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString(
                        environment.getProperty("ws.customer.save.endpoint")
                );

        HttpEntity<CustomerDTO> requestEntity = new HttpEntity<>(customerDTO);

        return this.userPasswordRestTemplate.exchange(
                uriComponentsBuilder.build().toUri(),
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<Object>() {
                }
        );
    }

    @Override
    public ResponseEntity<CustomerDTO> update(CustomerDTO customerDTO) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString(
                        environment.getProperty("ws.customer.update.endpoint")
                );

        HttpEntity<CustomerDTO> requestEntity = new HttpEntity<>(customerDTO);

        return this.userPasswordRestTemplate.exchange(
                uriComponentsBuilder.build().toUri(),
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<CustomerDTO>() {
                }
        );
    }

    @Override
    public ResponseEntity deleteById(Long customerId) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString(
                        this.environment.getProperty("ws.customer.delete.endpoint")
                );

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("customerId", String.valueOf(customerId));

        return this.userPasswordRestTemplate.exchange(
                uriComponentsBuilder.buildAndExpand(uriParams).toUri(),
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<Object>() {
                }
        );
    }
}
