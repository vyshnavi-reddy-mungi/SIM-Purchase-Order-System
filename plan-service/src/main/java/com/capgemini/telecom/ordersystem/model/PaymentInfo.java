package com.capgemini.telecom.ordersystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "payment_info")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "amount")
    private double amount;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "customer_id")
    private String customerId;

}
