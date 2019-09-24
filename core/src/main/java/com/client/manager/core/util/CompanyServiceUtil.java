package com.client.manager.core.util;

import com.client.manager.core.exception.CompanyNotFoundException;
import com.client.manager.core.repository.CompanyRepository;
import com.client.manager.entities.Company;
import com.client.manager.entities.dto.CompanyDTO;
import com.client.manager.entities.status.StatusDefinedValue;
import com.client.manager.entities.util.CompanyUtil;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompanyServiceUtil {
    private CompanyServiceUtil() {
    }

    public static Supplier<Company> setupForCreate(CompanyDTO companyDTO) {
        return () -> CompanyUtil.updateCompanyBranches(
                BaseServiceUtil
                        .<Company>setupForCreate()
                        .apply(() -> CompanyUtil.buildEntityFrom(companyDTO))
        );
    }

    public static Function<CompanyDTO, Company> setupForUpdate(
            Function<Long, Optional<Company>> findById
    ) throws CompanyNotFoundException {
        return (companyDTO) -> CompanyUtil.updateCompanyBranches(
                BaseServiceUtil
                        .setupForUpdate(findById)
                        .apply(() -> CompanyUtil.buildEntityFrom(companyDTO))
        );
    }

    public static Function<Long, Company> setupForDelete(
            Function<Long, Optional<Company>> findById
    ) throws CompanyNotFoundException {
        return (companyId) -> CompanyUtil.copyBasePropertiesForAllBranchesFrom(
                BaseServiceUtil
                        .setupForDelete(findById)
                        .apply(companyId)
        );
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
