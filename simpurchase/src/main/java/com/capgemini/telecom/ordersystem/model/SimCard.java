package com.capgemini.telecom.ordersystem.model;

import com.capgemini.telecom.ordersystem.enums.SimCardStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "simcards")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimCard {


    @Id
    @Column(name = "sim_number")
    private String simNumber;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "plan_id")
    private String planId;

    @Enumerated(EnumType.STRING)
    private SimCardStatus status;

    @Column(name = "plan_start_date")
    private LocalDate planStartDate;

    @Column(name = "plan_end_date")
    private LocalDate planEndDate;

    @Column(name = "estimated_cost")
    private double estimatedCost;

    @Column(name = "purchase-date")
    private LocalDateTime purchaseDate;
}
