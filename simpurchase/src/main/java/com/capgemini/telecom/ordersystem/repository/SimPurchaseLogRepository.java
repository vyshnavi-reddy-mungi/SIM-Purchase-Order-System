package com.capgemini.telecom.ordersystem.repository;

import com.capgemini.telecom.ordersystem.model.SimPurchaseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimPurchaseLogRepository extends JpaRepository<SimPurchaseLog, String> {
}
