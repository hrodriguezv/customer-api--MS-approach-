package com.client.manager.core.service;

import com.client.manager.entities.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByUsername(String username);
}
