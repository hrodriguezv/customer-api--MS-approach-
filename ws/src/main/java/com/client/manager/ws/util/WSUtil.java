package com.client.manager.ws.util;

import com.client.manager.entities.Customer;
import com.client.manager.entities.dto.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class WSUtil {
    private WSUtil() {
    }

    public static <T> Page<T> buildPageFrom(List<T> list, Pageable pageable, Long totalElements) {
        return new PageImpl<>(list, pageable, totalElements);
    }

    public static CustomerDTO restoreCustomerPrivateProperties(CustomerDTO target, Customer source) {
        target.setPassword(source.getPassword());
        return target;
    }
}
