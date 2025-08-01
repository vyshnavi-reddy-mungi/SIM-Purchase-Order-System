package com.capgemini.telecom.ordersystem.repository;


import com.capgemini.telecom.ordersystem.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, String> {

    Users findByUsername(String username);
}
