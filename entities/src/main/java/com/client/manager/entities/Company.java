package com.client.manager.entities;

import com.client.manager.entities.status.StatusDefinedValue;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Company extends BaseEntityProperties {
    @NotNull
    private String name;
    private String description;
    @NotNull
    private String address;
    @NotNull
    private Boolean trunk = Boolean.TRUE;
    @ManyToOne
    private Company parent;
    @OneToMany(mappedBy = "parent")
    private List<Company> companyBranches = new ArrayList<>();
    @OneToMany(mappedBy = "company")
    private List<Customer> customers = new ArrayList<>();
    @OneToMany(mappedBy = "company")
    private List<User> users = new ArrayList<>();

    public Company(
            Long id,
            StatusDefinedValue status,
            ZonedDateTime createdDate,
            ZonedDateTime updatedDate,
            String name,
            String description,
            String address,
            Boolean trunk,
            Company parent,
            List<Company> companyBranches,
            List<Customer> customers,
            List<User> users
    ) {
        super(id, status, createdDate, updatedDate);
        this.name = name;
        this.description = description;
        this.address = address;
        this.trunk = trunk;
        this.parent = parent;
        this.companyBranches = companyBranches;
        this.customers = customers;
        this.users = users;
    }
}
