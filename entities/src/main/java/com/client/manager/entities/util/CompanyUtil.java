package com.client.manager.entities.util;

import com.client.manager.entities.Company;
import com.client.manager.entities.dto.CompanyDTO;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class CompanyUtil {
    private CompanyUtil() {
    }

    public static Company setBasePropertiesForAllBranchesFrom(Company company) {
        company.getCompanyBranches().forEach(c -> {
            EntityUtil.copyBaseProperties(c, company);
            CompanyUtil.setBasePropertiesForAllBranchesFrom(c);
        });
        return company;
    }

    public static Company updateCompanyBranches(Company parent) {
        parent.getCompanyBranches().forEach(c ->
                CompanyUtil.setParentToChildCompany(
                        c,
                        parent.getTrunk() ? parent : null
                )
        );

        CompanyUtil.setBasePropertiesForAllBranchesFrom(parent);
        return parent;
    }

    public static Company setParentToChildCompany(Company child, Company parent) {
        child.setParent(parent);
        return child;
    }

    public static CompanyDTO buildHeadquarter(Company company) {
        return new CompanyDTO(
                company.getId(),
                company.getStatus(),
                company.getCreatedDate(),
                company.getUpdatedDate(),
                company.getName(),
                company.getDescription(),
                company.getAddress(),
                company.getTrunk(),
                null,
                company.getCompanyBranches()
                        .stream()
                        .map(CompanyUtil::buildHeadquarter)
                        .collect(Collectors.toList()),
                company.getCustomers()
                        .stream()
                        .map(CustomerUtil::buildLight)
                        .collect(Collectors.toList())
        );
    }

    public static Company buildHeadquarter(CompanyDTO company) {
        return new Company(
                company.getId(),
                company.getStatus(),
                company.getCreatedDate(),
                company.getUpdatedDate(),
                company.getName(),
                company.getDescription(),
                company.getAddress(),
                company.getTrunk(),
                null,
                Optional.ofNullable(company.getCompanyBranches())
                        .orElse(new ArrayList<>())
                        .stream()
                        .map(CompanyUtil::buildHeadquarter)
                        .collect(Collectors.toList()),
                Optional.ofNullable(company.getCustomers())
                        .orElse(new ArrayList<>())
                        .stream()
                        .map(CustomerUtil::buildLight)
                        .collect(Collectors.toList())
        );
    }

    public static Company buildLight(CompanyDTO company) {
        return new Company(
                company.getId(),
                company.getStatus(),
                company.getCreatedDate(),
                company.getUpdatedDate(),
                company.getName(),
                company.getDescription(),
                company.getAddress(),
                company.getTrunk(),
                null,
                null,
                null
        );
    }

    public static CompanyDTO buildLight(Company company) {
        return new CompanyDTO(
                company.getId(),
                company.getStatus(),
                company.getCreatedDate(),
                company.getUpdatedDate(),
                company.getName(),
                company.getDescription(),
                company.getAddress(),
                company.getTrunk(),
                null,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
}
