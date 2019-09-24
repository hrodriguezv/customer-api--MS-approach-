package com.client.manager.entities.dto;

import com.client.manager.entities.status.StatusDefinedValue;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Data
public class CustomerDTO {
    protected StatusDefinedValue status;
    protected ZonedDateTime createdDate;
    protected ZonedDateTime updatedDate;
    private Long id;
    private String name;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String address;
    private CompanyDTO company;
}
