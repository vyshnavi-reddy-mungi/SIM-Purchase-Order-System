package com.capgemini.telecom.ordersystem.repository;

import com.capgemini.telecom.ordersystem.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, String> {
}
