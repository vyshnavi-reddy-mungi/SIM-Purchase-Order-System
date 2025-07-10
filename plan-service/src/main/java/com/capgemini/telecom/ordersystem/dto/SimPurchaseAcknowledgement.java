package com.capgemini.telecom.ordersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimPurchaseAcknowledgement {

    private String status;
    private double totalCost;
    private String referenceId;
    private SimCardRequestDTO simCard;
}
