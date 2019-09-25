package com.client.manager.entities.dto;

import com.client.manager.entities.status.StatusDefinedValue;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.Date;


@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerDTO extends BaseDTOProperties {
    private String name;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String address;
    private CompanyDTO company = new CompanyDTO();


    public CustomerDTO(
            Long id,
            StatusDefinedValue status,
            ZonedDateTime createdDate,
            ZonedDateTime updatedDate,
            String name,
            String lastName,
            String username,
            String password,
            String email,
            String address,
            CompanyDTO company
    ) {
        super(id, status, createdDate, updatedDate);
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.company = company;
    }

    public Date getCreatedDateAsDate() {
        if (this.createdDate == null) {
            return null;
        }
        return Date.from(this.createdDate.toInstant());
    }

    public Date getUpdatedDateAsDate() {
        if (this.updatedDate == null) {
            return null;
        }
        return Date.from(this.updatedDate.toInstant());
    }
}
