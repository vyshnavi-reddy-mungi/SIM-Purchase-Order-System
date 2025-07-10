package com.capgemini.telecom.ordersystem.model;

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
