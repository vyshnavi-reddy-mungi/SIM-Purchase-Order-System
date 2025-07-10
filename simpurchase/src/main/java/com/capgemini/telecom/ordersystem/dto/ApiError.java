package com.capgemini.telecom.ordersystem.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private String errorCode;
    private String errorMessage;

}
