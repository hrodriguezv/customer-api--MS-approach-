package com.client.manager.ui.service.impl;

import com.client.manager.entities.dto.CompanyDTO;
import com.client.manager.entities.dto.PageDTO;
import com.client.manager.ui.service.ICompanyService;
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
public class CompanyServiceImpl implements ICompanyService {

    private final OAuth2RestTemplate userPasswordRestTemplate;
    private final Environment environment;

    public CompanyServiceImpl(OAuth2RestTemplate userPasswordRestTemplate, Environment environment) {
        this.userPasswordRestTemplate = userPasswordRestTemplate;
        this.environment = environment;
    }

    @Override
    public ResponseEntity<CompanyDTO> getById(Long companyId) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString(
                        this.environment.getProperty("ws.company.findbyid.endpoint")
                );

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("companyId", String.valueOf(companyId));

        return this.userPasswordRestTemplate.exchange(
                uriComponentsBuilder.buildAndExpand(uriParams).toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<CompanyDTO>() {
                }
        );
    }

    @Override
    public ResponseEntity<PageDTO<CompanyDTO>> getList(Pageable pageable) {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString(
                        this.environment.getProperty("ws.company.find.endpoint")
                );

        Optional.ofNullable(pageable)
                .ifPresent(p -> UIUtil.setPageableParameters(p, uriComponentsBuilder));

        return this.userPasswordRestTemplate.exchange(
                uriComponentsBuilder.build().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDTO<CompanyDTO>>() {
                }
        );
    }

    @Override
    public ResponseEntity save(CompanyDTO companyDTO) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString(
                        environment.getProperty("ws.company.save.endpoint")
                );

        HttpEntity<CompanyDTO> requestEntity = new HttpEntity<>(companyDTO);

        return this.userPasswordRestTemplate.exchange(
                uriComponentsBuilder.build().toUri(),
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<Object>() {
                }
        );
    }

    @Override
    public ResponseEntity<CompanyDTO> update(CompanyDTO companyDTO) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString(
                        environment.getProperty("ws.company.update.endpoint")
                );

        HttpEntity<CompanyDTO> requestEntity = new HttpEntity<>(companyDTO);

        return this.userPasswordRestTemplate.exchange(
                uriComponentsBuilder.build().toUri(),
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<CompanyDTO>() {
                }
        );
    }

    @Override
    public ResponseEntity deleteById(Long companyId) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString(
                        this.environment.getProperty("ws.company.delete.endpoint")
                );

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("companyId", String.valueOf(companyId));

        return this.userPasswordRestTemplate.exchange(
                uriComponentsBuilder.buildAndExpand(uriParams).toUri(),
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<Object>() {
                }
        );
    }
}
