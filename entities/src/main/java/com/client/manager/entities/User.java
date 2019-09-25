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
public class User extends BaseEntityProperties {
    @NotNull
    private String roles;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @ManyToOne
    @NotNull
    private Company company;

    public User(
            Long id,
            StatusDefinedValue status,
            ZonedDateTime createdDate,
            ZonedDateTime updatedDate,
            String roles,
            String username,
            String password,
            Company company
    ) {
        super(id, status, createdDate, updatedDate);
        this.roles = roles;
        this.username = username;
        this.password = password;
        this.company = company;
    }
}
