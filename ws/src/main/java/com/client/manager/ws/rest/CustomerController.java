package com.client.manager.ws.rest;

import com.client.manager.core.service.ICustomerService;
import com.client.manager.entities.Customer;
import com.client.manager.entities.dto.CustomerDTO;
import com.client.manager.entities.util.CustomerUtil;
import com.client.manager.entities.util.EntityUtil;
import com.client.manager.entities.util.StatusUtil;
import com.client.manager.ws.exception.CustomerNotFoundException;
import com.client.manager.ws.util.WSUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomer(
            @PathVariable Long customerId
    ) throws CustomerNotFoundException {
        return customerService
                .findById(customerId)
                .map(CustomerUtil::build)
                .orElseThrow(CustomerNotFoundException::new);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CustomerDTO> getCustomers(
            @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String criteria,
            @PageableDefault(
                    sort = {"id"},
                    direction = Sort.Direction.ASC
            ) Pageable pageable
    ) {
        Page<Customer> customerPage = customerService.find(
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
        return WSUtil.buildPageFrom(
                customerPage
                        .get()
                        .map(CustomerUtil::build)
                        .collect(Collectors.toList()),
                pageable,
                customerPage.getTotalElements()
        );
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void createCustomer(
            @RequestBody CustomerDTO customer
    ) {
        customerService.save(
                EntityUtil.setCreateOrUpdateBaseProperties(
                        CustomerUtil.build(customer)
                )
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(
            @RequestBody CustomerDTO customer
    ) throws CustomerNotFoundException {
        return CustomerUtil.build(
                customerService.update(
                        EntityUtil.setCreateOrUpdateBaseProperties(
                                EntityUtil.copyBaseProperties(
                                        CustomerUtil.build(
                                                customer
                                        ),
                                        customerService
                                                .findById(customer.getId())
                                                .orElseThrow(CustomerNotFoundException::new)
                                )
                        )
                )
        );
    }

    @DeleteMapping(value = "/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO deleteCustomer(
            @PathVariable Long customerId
    ) throws CustomerNotFoundException {
        return CustomerUtil.build(
                customerService.save(
                        EntityUtil.setDeleteBaseProperties(
                                customerService
                                        .findById(customerId)
                                        .orElseThrow(CustomerNotFoundException::new)
                        )
                )
        );
    }
}
