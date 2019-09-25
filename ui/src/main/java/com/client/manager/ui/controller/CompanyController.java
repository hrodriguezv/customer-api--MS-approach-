package com.client.manager.ui.controller;

import com.client.manager.entities.dto.CompanyDTO;
import com.client.manager.entities.dto.PageDTO;
import com.client.manager.ui.service.ICompanyService;
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
@RequestMapping("/company")
public class CompanyController {

    private final ICompanyService companyService;

    public CompanyController(ICompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/delete/{companyId}")
    public String deleteCompany(@PathVariable Long companyId) {
        this.companyService.deleteById(companyId);

        return "redirect:/company/list";
    }

    @PostMapping(value = "/update")
    public String updateCompany(@ModelAttribute("company") CompanyDTO company) {
        this.companyService.update(company);

        return "redirect:/company/list";
    }

    @GetMapping("/edit/{companyId}")
    public ModelAndView showEditCompanyPage(@PathVariable Long companyId) {
        ModelAndView mav = new ModelAndView("company-edit");
        CompanyDTO company = this.companyService.getById(companyId).getBody();

        PageDTO<CompanyDTO> companyPage = this.companyService
                .getList(null)
                .getBody();

        mav.addObject("company", company);

        mav.addObject(
                "companies",
                Optional.ofNullable(companyPage)
                        .map(PageDTO::getContent)
                        .orElse(new ArrayList<>())
                        .stream()
                        .filter(c -> !c.getId().equals(company.getId()))
                        .collect(Collectors.toList())
        );

        return mav;
    }

    @PostMapping(value = "/save")
    public String saveCompany(@ModelAttribute("company") CompanyDTO company) {
        this.companyService.save(company);

        return "redirect:/company/list";
    }

    @GetMapping("/new")
    public String showCompanyNewPage(Model model) {
        CompanyDTO company = new CompanyDTO(new CompanyDTO());
        PageDTO<CompanyDTO> companyPage = this.companyService
                .getList(null)
                .getBody();

        model.addAttribute("company", company);

        model.addAttribute(
                "companies",
                Optional.ofNullable(companyPage)
                        .map(PageDTO::getContent)
                        .orElse(new ArrayList<>())
        );

        return "company-new";
    }

    @GetMapping("/list")
    public String showCompanyListPage(
            @PageableDefault(
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable,
            final Model model
    ) {

        Optional.ofNullable(
                this.companyService
                        .getList(
                                pageable
                        )
        )
                .map(ResponseEntity::getBody)
                .ifPresent((companyPage) -> this.setupCompanyListView(model, companyPage));

        return "company-list";
    }

    private void setupCompanyListView(final Model model, PageDTO<CompanyDTO> companyPage) {
        int totalPages = companyPage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("companyPage", companyPage);
    }
}
