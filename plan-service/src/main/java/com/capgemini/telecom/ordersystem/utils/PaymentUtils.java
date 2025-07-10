package com.capgemini.telecom.ordersystem.utils;

import com.capgemini.telecom.ordersystem.exception.InsufficientAmountException;

public class PaymentUtils {

    public static void validateCreditLimit(double paidAmount) throws InsufficientAmountException {
        if(paidAmount>100){
            throw new InsufficientAmountException("Insufficient funds....!");
        }

    }
}
