package com.client.manager.core.service.impl;

import com.client.manager.core.repository.CustomerRepository;
import com.client.manager.core.service.ICustomerService;
import com.client.manager.entities.Customer;
import com.client.manager.entities.status.StatusDefinedValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Customer> find(Long companyId, String criteria, StatusDefinedValue status, Pageable pageable) {
        return customerRepository.findAll(companyId, criteria, status.ordinal(), pageable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer update(Customer customer) {
        return this.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }
}
