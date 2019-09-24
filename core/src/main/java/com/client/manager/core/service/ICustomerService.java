package com.client.manager.core.service;

import com.client.manager.entities.Customer;
import com.client.manager.entities.status.StatusDefinedValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService extends IBaseService<Customer, Long> {
    Page<Customer> find(Long companyId, String criteria, StatusDefinedValue status, Pageable pageable);

    Long count(String username);
}
