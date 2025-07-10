package com.capgemini.telecom.ordersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.capgemini.telecom.ordersystem.enums.SimCardStatus;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimCardResponseDTO {

    private String simNumber;
    private String customerId;
    private String planId;
    private SimCardStatus status;
    private LocalDate planStartDate;
    private LocalDate planEndDate;
    private double estimatedCost;
    private String message;
    private boolean success;
}
