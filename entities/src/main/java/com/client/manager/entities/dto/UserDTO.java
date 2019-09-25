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
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDTO extends BaseDTOProperties {
    private String roles;
    private String username;
    private String password;
    private CompanyDTO company = new CompanyDTO();

    public UserDTO(
            Long id,
            StatusDefinedValue status,
            ZonedDateTime createdDate,
            ZonedDateTime updatedDate,
            String roles,
            String username,
            String password,
            CompanyDTO company
    ) {
        super(id, status, createdDate, updatedDate);
        this.roles = roles;
        this.username = username;
        this.password = password;
        this.company = company;
    }
}
