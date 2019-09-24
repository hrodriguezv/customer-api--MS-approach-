package com.client.manager.entities.dto;

import com.client.manager.entities.status.StatusDefinedValue;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Data
public class CompanyDTO extends BaseDTOProperties {
    private String name;
    private String description;
    private String address;
    private Boolean trunk;
    private CompanyDTO parent;
    private List<CompanyDTO> companyBranches = new ArrayList<>();
    private List<CustomerDTO> customers = new ArrayList<>();

    public CompanyDTO(
            Long id,
            StatusDefinedValue status,
            ZonedDateTime createdDate,
            ZonedDateTime updatedDate,
            String name, String description,
            String address,
            Boolean trunk,
            CompanyDTO parent,
            List<CompanyDTO> companyBranches,
            List<CustomerDTO> customers
    ) {
        super(id, status, createdDate, updatedDate);
        this.name = name;
        this.description = description;
        this.address = address;
        this.trunk = trunk;
        this.parent = parent;
        this.companyBranches = companyBranches;
        this.customers = customers;
    }
}
