package com.client.manager.core.util;

import com.client.manager.core.repository.CompanyRepository;
import com.client.manager.entities.Company;
import com.client.manager.entities.status.StatusDefinedValue;

public class CompanyServiceUtil {
    private CompanyServiceUtil() {
    }

    public static Company persistAllBranchesFrom(final CompanyRepository companyRepository, final Company company) {
        company.getCompanyBranches().forEach(c -> {
            Company child = companyRepository.findById(c.getId()).orElseThrow(IllegalArgumentException::new);

            child.setParent(c.getParent());
            child.setUpdatedDate(c.getUpdatedDate());
            child.setStatus(c.getStatus());

            companyRepository.save(child);

            CompanyServiceUtil.persistAllBranchesFrom(companyRepository, child);
        });
        return company;
    }

    public static Company deleteAllBranchesFrom(final CompanyRepository companyRepository, final Company company) {
        company.getCompanyBranches().forEach(c -> {
            Company child = companyRepository.findById(c.getId()).orElseThrow(IllegalArgumentException::new);

            child.setUpdatedDate(company.getUpdatedDate());
            child.setStatus(StatusDefinedValue.DISABLED);

            companyRepository.save(child);

            CompanyServiceUtil.deleteAllBranchesFrom(companyRepository, child);
        });
        return company;
    }
}
