package com.client.manager.core.service;

import com.client.manager.entities.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICompanyService extends IBaseService<Company, Long> {
    Page<Company> find(Pageable pageable);
}
