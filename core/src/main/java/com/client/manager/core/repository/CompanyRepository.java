package com.client.manager.core.repository;

import com.client.manager.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query(
            value = "SELECT " +
                    " COUNT(co) " +
                    "FROM Company co " +
                    "WHERE " +
                    "LOWER(co.name) = ?1 "
    )
    Long count(String name);
}
