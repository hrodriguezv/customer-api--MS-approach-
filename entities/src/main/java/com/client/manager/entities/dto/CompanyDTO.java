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
@EqualsAndHashCode(of = "id")
@Data
public class CompanyDTO {
    protected StatusDefinedValue status;
    protected ZonedDateTime createdDate;
    protected ZonedDateTime updatedDate;
    private Long id;
    private String name;
    private String description;
    private String address;
    private Boolean trunk;
    private CompanyDTO parent;
    private List<CompanyDTO> companyBranches = new ArrayList<>();
    private List<CustomerDTO> customers = new ArrayList<>();
}
