package com.capgemini.telecom.ordersystem.model;

import com.capgemini.telecom.ordersystem.enums.SimCardStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.Map;

@Entity(name = "sim-purchase-log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimPurchaseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "status_counts")
    private Map<SimCardStatus, Long> statusCounts;



}
