package com.client.manager.ws.rest;

import com.client.manager.core.exception.CompanyNotFoundException;
import com.client.manager.core.service.ICompanyService;
import com.client.manager.core.util.CompanyServiceUtil;
import com.client.manager.entities.Company;
import com.client.manager.entities.dto.CompanyDTO;
import com.client.manager.entities.dto.PageDTO;
import com.client.manager.entities.util.CompanyUtil;
import com.client.manager.ws.util.WSUtil;
import com.client.manager.ws.validations.CompanyControllerValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final ICompanyService companyService;

    public CompanyController(ICompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(value = "/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public CompanyDTO getCompany(
            @PathVariable Long companyId
    ) {
        return companyService
                .findById(companyId)
                .map(CompanyUtil::buildDTOFrom)
                .orElseThrow(CompanyNotFoundException::new);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageDTO<CompanyDTO> getCompanies(Pageable pageable) {
        Page<Company> companyPage = this.companyService.find(pageable);
        return PageDTO.buildFrom(
                WSUtil.buildPageFrom(
                        companyPage
                                .get()
                                .map(CompanyUtil::buildDTOFrom)
                                .collect(Collectors.toList()),
                        pageable,
                        companyPage.getTotalElements()
                )
        );
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void createCompany(
            @RequestBody CompanyDTO company
    ) {
        CompanyControllerValidation.validateCompany(
                company,
                () -> this.companyService.count(company.getName().toLowerCase())
        );

        this.companyService.save(
                CompanyServiceUtil
                        .setupForCreate(company)
                        .get()
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CompanyDTO updateCompany(
            @RequestBody CompanyDTO company
    ) {
        Company existingCompany = this.companyService
                .findById(company.getId())
                .orElseThrow(CompanyNotFoundException::new);

        CompanyControllerValidation.validateCompany(
                company,
                existingCompany,
                () -> this.companyService.count(company.getName().toLowerCase())
        );

        return CompanyUtil.buildDTOFrom(
                this.companyService.update(
                        CompanyServiceUtil
                                .setupForUpdate(existingCompany)
                                .apply(company)
                )
        );
    }

    @DeleteMapping(value = "/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCompany(
            @PathVariable Long companyId
    ) {
        Company existingCompany = this.companyService
                .findById(companyId)
                .orElseThrow(CompanyNotFoundException::new);

        this.companyService.save(
                CompanyServiceUtil
                        .setupForDelete(existingCompany)
                        .apply(companyId)
        );
    }
}
