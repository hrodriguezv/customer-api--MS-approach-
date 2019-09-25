package com.client.manager.core.service.impl;

import com.client.manager.core.repository.UserRepository;
import com.client.manager.core.service.IUserService;
import com.client.manager.entities.User;
import com.client.manager.entities.status.StatusDefinedValue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsernameAndStatus(username, StatusDefinedValue.ENABLED);
    }
}
