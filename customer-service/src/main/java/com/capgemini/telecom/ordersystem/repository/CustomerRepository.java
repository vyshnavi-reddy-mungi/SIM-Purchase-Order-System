package com.capgemini.telecom.ordersystem.repository;

import com.capgemini.telecom.ordersystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
