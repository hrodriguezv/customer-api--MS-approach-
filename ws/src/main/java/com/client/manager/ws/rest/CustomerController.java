package com.client.manager.ws.rest;

import com.client.manager.core.exception.CustomerNotFoundException;
import com.client.manager.core.service.ICustomerService;
import com.client.manager.core.util.BaseServiceUtil;
import com.client.manager.entities.Customer;
import com.client.manager.entities.dto.CustomerDTO;
import com.client.manager.entities.dto.PageDTO;
import com.client.manager.entities.util.CustomerUtil;
import com.client.manager.entities.util.StatusUtil;
import com.client.manager.ws.util.SecurityUtil;
import com.client.manager.ws.util.WSUtil;
import com.client.manager.ws.validations.CustomerControllerValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final ICustomerService customerService;
    private final PasswordEncoder encoder;

    public CustomerController(ICustomerService customerService, PasswordEncoder encoder) {
        this.customerService = customerService;
        this.encoder = encoder;
    }

    @GetMapping(value = "/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomer(
            @PathVariable Long customerId
    ) {
        return customerService
                .findById(customerId)
                .map(CustomerUtil::buildDTOFrom)
                .orElseThrow(CustomerNotFoundException::new);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageDTO<CustomerDTO> getCustomers(
            @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String criteria,
            Pageable pageable
    ) {
        Page<Customer> customerPage = this.customerService.find(
                Optional.ofNullable(companyId)
                        .orElse(0L),
                Optional.ofNullable(criteria)
                        .map(String::toLowerCase)
                        .orElse(""),
                StatusUtil.getStatusFrom(
                        Optional.ofNullable(status)
                                .orElse("")
                ),
                pageable
        );
        return PageDTO.buildFrom(
                WSUtil.buildPageFrom(
                        customerPage
                                .get()
                                .map(CustomerUtil::buildDTOFrom)
                                .collect(Collectors.toList()),
                        pageable,
                        customerPage.getTotalElements()
                )
        );
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)

    public void createCustomer(
            @RequestBody CustomerDTO customer
    ) {
        CustomerControllerValidation.validateCustomer(
                customer,
                () -> this.customerService.count(customer.getUsername())
        );

        SecurityUtil.encryptPassword(customer, this.encoder);

        Customer customerToCreate = CustomerUtil.buildEntityFrom(customer);

        this.customerService.save(
                BaseServiceUtil
                        .<Customer>setupForCreate()
                        .apply(customerToCreate)
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(
            @RequestBody CustomerDTO customer
    ) {
        Customer existingCustomer = this.customerService
                .findById(customer.getId())
                .orElseThrow(CustomerNotFoundException::new);

        WSUtil.restoreCustomerPrivateProperties(customer, existingCustomer);

        CustomerControllerValidation.validateCustomer(
                customer,
                existingCustomer,
                () -> this.customerService.count(customer.getUsername())
        );

        Customer customerToUpdate = CustomerUtil.buildEntityFrom(customer);

        return CustomerUtil.buildDTOFrom(
                this.customerService.update(
                        BaseServiceUtil
                                .setupForUpdate(existingCustomer)
                                .apply(customerToUpdate)
                )
        );
    }

    @DeleteMapping(value = "/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(
            @PathVariable Long customerId
    ) {
        Customer existingCustomer = this.customerService
                .findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        this.customerService.save(
                BaseServiceUtil
                        .setupForDelete(existingCustomer)
                        .get()
        );
    }
}
