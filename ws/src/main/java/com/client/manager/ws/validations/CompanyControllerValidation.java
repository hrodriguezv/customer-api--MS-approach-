package com.client.manager.ws.validations;

import com.client.manager.core.exception.DuplicatedCompanyException;
import com.client.manager.entities.Company;
import com.client.manager.entities.dto.CompanyDTO;
import com.client.manager.entities.dto.validations.CompanyValidation;

import java.util.function.Supplier;

public class CompanyControllerValidation {

    private CompanyControllerValidation() {
    }

    public static void validateDuplicatedOnCreate(Supplier<Long> countRepeatedSupplier) {
        BaseControllerValidation.validateDuplicatedOnCreate(countRepeatedSupplier, DuplicatedCompanyException::new);
    }

    public static void validateDuplicatedOnUpdate(
            CompanyDTO companyToUpdate,
            Company existingCompany,
            Supplier<Long> countRepeatedSupplier
    ) {
        if (!existingCompany.getName().equals(companyToUpdate.getName()) && countRepeatedSupplier.get() > 0) {
            throw new DuplicatedCompanyException();
        }
    }

    public static void validateCompany(
            CompanyDTO companyToUpdate,
            Company existingCompany,
            Supplier<Long> countRepeatedSupplier
    ) {
        CompanyValidation.validate(companyToUpdate);
        CompanyControllerValidation.validateDuplicatedOnUpdate(companyToUpdate, existingCompany, countRepeatedSupplier);
    }

    public static void validateCompany(CompanyDTO companyToUpdate, Supplier<Long> countRepeatedSupplier) {
        CompanyValidation.validate(companyToUpdate);
        CompanyControllerValidation.validateDuplicatedOnCreate(countRepeatedSupplier);
    }

}
