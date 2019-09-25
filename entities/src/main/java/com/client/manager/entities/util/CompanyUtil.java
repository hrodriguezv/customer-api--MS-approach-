package com.client.manager.entities.util;

import com.client.manager.entities.Company;
import com.client.manager.entities.dto.CompanyDTO;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class CompanyUtil {
    private CompanyUtil() {
    }

    public static Company copyBasePropertiesForAllBranchesFrom(Company parent) {
        parent.getCompanyBranches().forEach(c -> {
            EntityUtil.copyBaseProperties(c, parent);
            CompanyUtil.copyBasePropertiesForAllBranchesFrom(c);
        });
        return parent;
    }

    public static Company updateCompanyBranches(Company parent) {
        parent.getCompanyBranches().forEach(c ->
                {
                    CompanyUtil.configureParentToChild(
                            c,
                            parent.getTrunk() ? parent : null
                    );

                    CompanyUtil.updateCompanyBranches(c);
                }
        );

        return parent;
    }

    public static void configureParentToChild(Company child, Company parent) {
        child.setParent(parent);
        EntityUtil.copyBaseProperties(child, parent);
    }

    public static CompanyDTO buildDTOFrom(Company company) {
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
                        .map(CompanyUtil::buildDTOFrom)
                        .collect(Collectors.toList()),
                company.getCustomers()
                        .stream()
                        .map(CustomerUtil::buildLightDTOFrom)
                        .collect(Collectors.toList()),
                company.getUsers()
                        .stream()
                        .map(UserUtil::buildLightDTOFrom)
                        .collect(Collectors.toList())
        );
    }

    public static Company buildEntityFrom(CompanyDTO company) {
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
                        .map(CompanyUtil::buildEntityFrom)
                        .collect(Collectors.toList()),
                Optional.ofNullable(company.getCustomers())
                        .orElse(new ArrayList<>())
                        .stream()
                        .map(CustomerUtil::buildLightEntityFrom)
                        .collect(Collectors.toList()),
                Optional.ofNullable(company.getUsers())
                        .orElse(new ArrayList<>())
                        .stream()
                        .map(UserUtil::buildLightEntityFrom)
                        .collect(Collectors.toList())
        );
    }

    public static Company buildLightEntityFrom(CompanyDTO company) {
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
                null,
                null
        );
    }

    public static CompanyDTO buildLightDTOFrom(Company company) {
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
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
}
