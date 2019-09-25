package com.client.manager.ui.util;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

public class UIUtil {
    private UIUtil() {
    }

    public static void setPageableParameters(Pageable pageable, UriComponentsBuilder uriComponentsBuilder) {
        uriComponentsBuilder
                .queryParam(
                        "page",
                        pageable.getPageNumber() > 0 ? pageable.getPageNumber() - 1 : 0
                )
                .queryParam("size", pageable.getPageSize());

        Optional<Sort.Order> orderOptional = pageable.getSort()
                .stream()
                .findFirst();

        orderOptional.ifPresent(order -> UIUtil.setSortParameters(order, uriComponentsBuilder));
    }

    public static void setSortParameters(Sort.Order order, UriComponentsBuilder uriComponentsBuilder) {
        uriComponentsBuilder.queryParam(
                "sort",
                order.getProperty()
                        + ","
                        + order.getDirection()
        );
    }
}
