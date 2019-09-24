package com.client.manager.core.validations;

import com.client.manager.core.exception.DuplicatedCompanyException;
import com.client.manager.entities.Company;
import com.client.manager.entities.dto.CompanyDTO;

import java.util.function.Supplier;

public class CompanyServiceValidation {

    private CompanyServiceValidation() {
    }

    public static void validateDuplicatedOnCreate(Supplier<Long> countRepeatedSupplier) {
        if (countRepeatedSupplier.get() > 0) {
            throw new DuplicatedCompanyException();
        }
    }

    public static void validateDuplicatedOnUpdate(
            CompanyDTO companyToUpdate,
            Supplier<Company> findById,
            Supplier<Long> countRepeatedSupplier
    ) {
        Company existingCompany = findById.get();

        if (!existingCompany.getName().equals(companyToUpdate.getName()) && countRepeatedSupplier.get() > 0) {
            throw new DuplicatedCompanyException();
        }
    }

}
