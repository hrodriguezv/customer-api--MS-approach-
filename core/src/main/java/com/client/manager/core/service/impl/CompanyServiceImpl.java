package com.client.manager.core.service.impl;

import com.client.manager.core.exception.CompanyNotFoundException;
import com.client.manager.core.repository.CompanyRepository;
import com.client.manager.core.service.ICompanyService;
import com.client.manager.core.util.CompanyServiceUtil;
import com.client.manager.entities.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CompanyServiceImpl implements ICompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Company> find(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Company save(Company company) {
        companyRepository.save(company);
        CompanyServiceUtil.persistAllBranchesFrom(companyRepository, company);
        return company;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Company update(Company company) {
        Company companyToUpdate = companyRepository
                .findById(company.getId())
                .orElseThrow(CompanyNotFoundException::new);
        CompanyServiceUtil.deleteAllBranchesFrom(companyRepository, companyToUpdate);
        return this.save(company);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Long count(String name) {
        return companyRepository.count(name);
    }
}
