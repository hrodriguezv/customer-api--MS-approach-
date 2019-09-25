package com.client.manager.entities.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class PageDTO<T> {
    private List<T> content;
    private Long totalElements;
    private Integer totalPages;
    private Integer number;
    private Integer size;

    public static <T> PageDTO<T> buildFrom(Page<T> page) {
        return new PageDTO<>(
                page.getContent(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize()
        );
    }
}
