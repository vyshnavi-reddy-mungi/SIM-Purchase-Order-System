package com.capgemini.telecom.ordersystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiErrorResponseDTO {

    private String errorCode;
    private String errorMessage;
}
