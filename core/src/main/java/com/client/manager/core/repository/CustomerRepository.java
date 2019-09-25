package com.client.manager.core.repository;

import com.client.manager.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(
            value = "SELECT " +
                    "cu " +
                    "FROM Customer cu " +
                    "JOIN cu.company co " +
                    "WHERE " +
                    "( " +
                    "CAST(?1 as string) = '0' " +
                    "OR " +
                    "cu.company.id = ?1" +
                    ") " +
                    "AND " +
                    "( " +
                    "LENGTH(?2) = 0 " +
                    "OR " +
                    "(" +
                    "LOWER(cu.name) LIKE %?2% " +
                    "OR " +
                    "LOWER(cu.lastName) LIKE %?2% " +
                    "OR " +
                    "LOWER(cu.address) LIKE %?2% " +
                    "OR " +
                    "cu.username LIKE %?2% " +
                    "OR " +
                    "LOWER(cu.email) LIKE %?2% " +
                    ")" +
                    ") " +
                    "AND " +
                    "( " +
                    "CAST(?3 as string) = '0' " +
                    "OR " +
                    "cu.status = ?3" +
                    ") "
    )
    Page<Customer> findAll(Long companyId, String criteria, Integer status, Pageable pageable);

    @Query(
            value = "SELECT " +
                    " COUNT(cu) " +
                    "FROM Customer cu " +
                    "WHERE " +
                    "cu.username = ?1 "
    )
    Long count(String username);
}
