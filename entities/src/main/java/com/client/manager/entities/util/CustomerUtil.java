package com.client.manager.entities.util;

import com.client.manager.entities.Customer;
import com.client.manager.entities.dto.CustomerDTO;

public class CustomerUtil {
    private CustomerUtil() {
    }

    public static CustomerDTO build(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getStatus(),
                customer.getCreatedDate(),
                customer.getUpdatedDate(),
                customer.getName(),
                customer.getLastName(),
                customer.getUsername(),
                customer.getPassword(),
                customer.getEmail(),
                customer.getAddress(),
                CompanyUtil.buildLightDTOFrom(customer.getCompany())
        );
    }

    public static Customer build(CustomerDTO customer) {
        return new Customer(
                customer.getId(),
                customer.getStatus(),
                customer.getCreatedDate(),
                customer.getUpdatedDate(),
                customer.getName(),
                customer.getLastName(),
                customer.getUsername(),
                customer.getPassword(),
                customer.getEmail(),
                customer.getAddress(),
                CompanyUtil.buildLightEntityFrom(customer.getCompany())
        );
    }

    public static CustomerDTO buildLight(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getStatus(),
                customer.getCreatedDate(),
                customer.getUpdatedDate(),
                customer.getName(),
                customer.getLastName(),
                customer.getUsername(),
                customer.getPassword(),
                customer.getEmail(),
                customer.getAddress(),
                null
        );
    }

    public static Customer buildLight(CustomerDTO customer) {
        return new Customer(
                customer.getId(),
                customer.getStatus(),
                customer.getCreatedDate(),
                customer.getUpdatedDate(),
                customer.getName(),
                customer.getLastName(),
                customer.getUsername(),
                customer.getPassword(),
                customer.getEmail(),
                customer.getAddress(),
                null
        );
    }
}
