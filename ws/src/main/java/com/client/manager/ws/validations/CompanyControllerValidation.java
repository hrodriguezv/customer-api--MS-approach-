package com.client.manager.ws.validations;

import com.client.manager.core.exception.CompanyNotFoundException;
import com.client.manager.core.exception.DuplicatedCompanyException;
import com.client.manager.entities.Company;
import com.client.manager.entities.dto.CompanyDTO;
import com.client.manager.entities.dto.validations.CompanyValidation;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompanyControllerValidation {

    private CompanyControllerValidation() {
    }

    public static void validateDuplicatedOnCreate(Supplier<Long> countRepeatedSupplier) {
        BaseControllerValidation.validateDuplicatedOnCreate(countRepeatedSupplier, DuplicatedCompanyException::new);
    }

    public static void validateDuplicatedOnUpdate(
            CompanyDTO companyToUpdate,
            Function<Long, Optional<Company>> findById,
            Supplier<Long> countRepeatedSupplier
    ) {
        Company existingCompany = findById
                .apply(companyToUpdate.getId())
                .orElseThrow(CompanyNotFoundException::new);

        if (!existingCompany.getName().equals(companyToUpdate.getName()) && countRepeatedSupplier.get() > 0) {
            throw new DuplicatedCompanyException();
        }
    }

    public static void validateCompany(
            CompanyDTO companyToUpdate,
            Function<Long, Optional<Company>> findById,
            Supplier<Long> countRepeatedSupplier
    ) {
        CompanyValidation.validate(companyToUpdate);
        CompanyControllerValidation.validateDuplicatedOnUpdate(companyToUpdate, findById, countRepeatedSupplier);
    }

    public static void validateCompany(CompanyDTO companyToUpdate, Supplier<Long> countRepeatedSupplier) {
        CompanyValidation.validate(companyToUpdate);
        CompanyControllerValidation.validateDuplicatedOnCreate(countRepeatedSupplier);
    }

}
