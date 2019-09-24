package com.client.manager.ws.rest;

import com.client.manager.core.service.ICompanyService;
import com.client.manager.entities.Company;
import com.client.manager.entities.dto.CompanyDTO;
import com.client.manager.entities.util.CompanyUtil;
import com.client.manager.entities.util.EntityUtil;
import com.client.manager.ws.exception.CompanyNotFoundException;
import com.client.manager.ws.util.WSUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
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
    ) throws CompanyNotFoundException {
        return companyService
                .findById(companyId)
                .map(CompanyUtil::buildHeadquarter)
                .orElseThrow(CompanyNotFoundException::new);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CompanyDTO> getCompanies(
            @PageableDefault(
                    sort = {"id"},
                    direction = Sort.Direction.ASC
            ) Pageable pageable
    ) {
        Page<Company> companyPage = companyService.find(pageable);
        return WSUtil.buildPageFrom(
                companyPage
                        .get()
                        .map(CompanyUtil::buildHeadquarter)
                        .collect(Collectors.toList()),
                pageable,
                companyPage.getTotalElements()
        );
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void createCompany(
            @RequestBody CompanyDTO company
    ) {
        companyService.save(
                CompanyUtil.updateCompanyBranches(
                        EntityUtil.setCreateOrUpdateBaseProperties(
                                CompanyUtil.buildHeadquarter(
                                        company
                                )
                        )
                )
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CompanyDTO updateCompany(
            @RequestBody CompanyDTO company
    ) throws CompanyNotFoundException {
        return CompanyUtil.buildHeadquarter(
                companyService.update(
                        CompanyUtil.updateCompanyBranches(
                                EntityUtil.setCreateOrUpdateBaseProperties(
                                        EntityUtil.setBaseProperties(
                                                CompanyUtil.buildHeadquarter(
                                                        company
                                                ),
                                                companyService.findById(company.getId())
                                                        .orElseThrow(CompanyNotFoundException::new)
                                        )
                                )
                        )
                )
        );
    }

    @DeleteMapping(value = "/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCompany(
            @PathVariable Long companyId
    ) throws CompanyNotFoundException {
        CompanyUtil.buildHeadquarter(
                companyService.save(
                        CompanyUtil.setBasePropertiesForAllBranchesFrom(
                                EntityUtil.setDeleteBaseProperties(
                                        companyService
                                                .findById(companyId)
                                                .orElseThrow(CompanyNotFoundException::new)
                                )
                        )
                )
        );
    }
}
