package com.capgemini.telecom.ordersystem.dto;

import com.capgemini.telecom.ordersystem.model.PaymentInfo;
import com.capgemini.telecom.ordersystem.model.SimCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimPurchaseRequest {

    private SimCard simcard;
    private PaymentInfo paymentInfo;
}
