package com.client.manager.entities;

import com.client.manager.entities.status.StatusDefinedValue;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Data
@MappedSuperclass
public abstract class BaseEntityProperties {
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    protected StatusDefinedValue status;
    @NotNull
    protected ZonedDateTime createdDate;
    @NotNull
    protected ZonedDateTime updatedDate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
