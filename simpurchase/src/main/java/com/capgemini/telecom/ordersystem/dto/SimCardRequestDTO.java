package com.capgemini.telecom.ordersystem.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimCardRequestDTO {

    @NotNull(message = "Sim number cannot be null or non-numeric")
    @Pattern(regexp = "^\\d{10}$", message = "invalid mobile number entered")
    private String simNumber;

    @NotEmpty(message = "Customer Id is required")
    @Size(min = 2, max = 25, message = "Customer id must be between 2 and 25 characters")
    private String customerId;

    @NotEmpty(message = "Plan id is required")
    @Size(min = 2, max = 25, message = "Plan id must be between 2 and 25 characters")
    private String planId;

    private PaymentInfoDTO payment;

}
