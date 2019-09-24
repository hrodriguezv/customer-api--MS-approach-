package com.client.manager.core.util;

import com.client.manager.core.exception.CompanyNotFoundException;
import com.client.manager.core.repository.CompanyRepository;
import com.client.manager.entities.Company;
import com.client.manager.entities.dto.CompanyDTO;
import com.client.manager.entities.util.CompanyUtil;
import com.client.manager.entities.util.EntityUtil;

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
        company.getCompanyBranches().forEach(newBranch -> {
            Company existingBranch = companyRepository
                    .findById(newBranch.getId())
                    .orElseThrow(CompanyNotFoundException::new);

            CompanyServiceUtil.copyNewBranchesPropertiesOnExistingBranches(existingBranch, newBranch);

            companyRepository.save(existingBranch);

            CompanyServiceUtil.persistAllBranchesFrom(companyRepository, newBranch);
        });
        return company;
    }

    public static Company deleteAllBranchesFrom(final CompanyRepository companyRepository, final Company company) {
        company.getCompanyBranches().forEach(branchToDelete -> {
            EntityUtil.applyBasePropertiesOnDelete(branchToDelete);

            companyRepository.save(branchToDelete);

            CompanyServiceUtil.deleteAllBranchesFrom(companyRepository, branchToDelete);
        });
        return company;
    }

    public static Company copyNewBranchesPropertiesOnExistingBranches(Company target, Company source) {
        target.setParent(source.getParent());
        target.setUpdatedDate(source.getUpdatedDate());
        target.setStatus(source.getStatus());

        return target;
    }
}
