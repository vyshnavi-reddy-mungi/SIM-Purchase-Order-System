package com.capgemini.telecom.ordersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {

    private String customerName;
    private String email;
    private boolean registered;
    private boolean verified;
    private boolean activated;
}
