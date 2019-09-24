package com.client.manager.entities;

import com.client.manager.entities.status.StatusDefinedValue;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Customer extends BaseEntityProperties {
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;
    @NotNull
    private String address;
    @ManyToOne
    @NotNull
    private Company company;

    public Customer(
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
            Company company
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
}
