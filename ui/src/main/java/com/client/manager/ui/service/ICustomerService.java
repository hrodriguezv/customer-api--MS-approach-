package com.client.manager.ui.service;

import com.client.manager.entities.dto.CustomerDTO;
import com.client.manager.entities.dto.PageDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ICustomerService extends IBaseService<CustomerDTO, Long> {
    ResponseEntity<PageDTO<CustomerDTO>> getList(
            Long companyId,
            String status,
            String criteria,
            Pageable pageable
    );
}
