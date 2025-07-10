package com.capgemini.telecom.ordersystem.dto;

import com.capgemini.telecom.ordersystem.enums.PlanPeriod;
import com.capgemini.telecom.ordersystem.enums.PlanType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class PlanDTO {

    @NotEmpty(message = "Plan name is required")
    @Size(min = 2, max = 100, message = "Plan name must be between 2 and 100 characters")
    private String planName;

    @Min(value = 0, message = "Data price cannot be negative")
    private double dataPrice;

    @Min(value = 0, message = "Internet price cannot be negative")
    private double internetPrice;

    @NotNull(message = "Plan type is required")
    private PlanType planType;

    @NotNull(message = "Plan period is required")
    private PlanPeriod planPeriod;

    private boolean recurring;
}
