package com.client.manager.ui.service;

import com.client.manager.entities.dto.CompanyDTO;
import com.client.manager.entities.dto.PageDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ICompanyService extends IBaseService<CompanyDTO, Long> {
    ResponseEntity<PageDTO<CompanyDTO>> getList(Pageable pageable);
}
