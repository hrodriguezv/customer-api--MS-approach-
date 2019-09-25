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
@EqualsAndHashCode(of = "id")
@Data
public abstract class BaseDTOProperties {
    private Long id;
    protected StatusDefinedValue status;
    protected ZonedDateTime createdDate;
    protected ZonedDateTime updatedDate;

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
