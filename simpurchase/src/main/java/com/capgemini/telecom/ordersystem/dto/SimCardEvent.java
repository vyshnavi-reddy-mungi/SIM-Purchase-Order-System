package com.capgemini.telecom.ordersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimCardEvent {

    private String message;
    private String status;
    private SimCardRequestDTO order;
}
