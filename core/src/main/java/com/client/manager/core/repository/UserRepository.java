package com.client.manager.core.repository;

import com.client.manager.entities.User;
import com.client.manager.entities.status.StatusDefinedValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndStatus(String username, StatusDefinedValue status);
}
