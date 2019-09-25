package com.client.manager.ui.controller;

import com.client.manager.entities.dto.CompanyDTO;
import com.client.manager.entities.dto.CustomerDTO;
import com.client.manager.entities.dto.PageDTO;
import com.client.manager.ui.service.ICompanyService;
import com.client.manager.ui.service.ICustomerService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final ICustomerService customerService;
    private final ICompanyService companyService;

    public CustomerController(ICustomerService customerService, ICompanyService companyService) {
        this.customerService = customerService;
        this.companyService = companyService;
    }

    @GetMapping("/delete/{customerId}")
    public String deleteCustomer(@PathVariable Long customerId) {
        this.customerService.deleteById(customerId);

        return "redirect:/customer/list";
    }

    @PostMapping(value = "/update")
    public String updateCustomer(@ModelAttribute("customer") CustomerDTO customer) {
        this.customerService.update(customer);

        return "redirect:/customer/list";
    }

    @GetMapping("/edit/{customerId}")
    public ModelAndView showEditCustomerPage(@PathVariable Long customerId) {
        ModelAndView mav = new ModelAndView("customer-edit");
        CustomerDTO customer = this.customerService.getById(customerId).getBody();

        PageDTO<CompanyDTO> companyPage = this.companyService
                .getList(null)
                .getBody();

        mav.addObject("customer", customer);

        mav.addObject(
                "companies",
                Optional.ofNullable(companyPage)
                        .map(PageDTO::getContent)
                        .orElse(new ArrayList<>())
        );

        return mav;
    }

    @PostMapping(value = "/save")
    public String saveCustomer(@ModelAttribute("customer") CustomerDTO customer) {
        this.customerService.save(customer);

        return "redirect:/customer/list";
    }

    @GetMapping("/new")
    public String showCustomerNewPage(Model model) {
        CustomerDTO customer = new CustomerDTO();
        PageDTO<CompanyDTO> companyPage = this.companyService
                .getList(null)
                .getBody();

        model.addAttribute("customer", customer);

        model.addAttribute(
                "companies",
                Optional.ofNullable(companyPage)
                        .map(PageDTO::getContent)
                        .orElse(new ArrayList<>())
        );

        return "customer-new";
    }

    @GetMapping("/list")
    public String showCustomerListPage(
            @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String criteria,
            @PageableDefault(
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable,
            final Model model
    ) {

        Optional.ofNullable(
                this.customerService
                        .getList(
                                companyId,
                                status,
                                criteria,
                                pageable
                        )
        )
                .map(ResponseEntity::getBody)
                .ifPresent((customerPage) -> this.setupCustomerListView(model, customerPage));

        PageDTO<CompanyDTO> companyPage = this.companyService
                .getList(null)
                .getBody();

        model.addAttribute(
                "companies",
                Optional.ofNullable(companyPage)
                        .map(PageDTO::getContent)
                        .orElse(new ArrayList<>())
        );

        return "customer-list";
    }

    private void setupCustomerListView(final Model model, PageDTO<CustomerDTO> customerPage) {
        int totalPages = customerPage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("customerPage", customerPage);
    }
}
