package com.capgemini.telecom.ordersystem.model;

import com.capgemini.telecom.ordersystem.enums.PlanPeriod;
import com.capgemini.telecom.ordersystem.enums.PlanType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "plans")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

    @Id
    private String id;

    @Column(name = "plan_name")
    private String planName;

    @Column(name = "data_price")
    private double dataPrice;

    @Column(name = "internet_price")
    private double internetPrice;

    @Enumerated(EnumType.STRING)
    private PlanType planType;

    @Enumerated(EnumType.STRING)
    private PlanPeriod planPeriod;

    private boolean recurring;
}
