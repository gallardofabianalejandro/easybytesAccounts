package com.nacionservicios.accounts.repository;

import com.nacionservicios.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("select c from Customer c where c.mobileNumber = ?1")
    Optional<Customer> findByMobileNumber(String mobileNumber);

}