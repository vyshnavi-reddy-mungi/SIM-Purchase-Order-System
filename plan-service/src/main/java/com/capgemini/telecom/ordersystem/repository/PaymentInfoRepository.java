package com.capgemini.telecom.ordersystem.repository;

import com.capgemini.telecom.ordersystem.model.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Integer> {
}
