package com.capgemini.telecom.ordersystem.repository;

import com.capgemini.telecom.ordersystem.model.SimCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SimCardRepository extends JpaRepository<SimCard, String> {

    Optional<SimCard> findBySimNumber(String simNumber);

}
