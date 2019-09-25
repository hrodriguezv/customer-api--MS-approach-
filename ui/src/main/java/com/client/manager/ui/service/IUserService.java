package com.client.manager.ui.service;

import com.client.manager.entities.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    ResponseEntity<UserDTO> findByUsername(String username);

    ResponseEntity<UserDTO> login(String username, String password);
}
