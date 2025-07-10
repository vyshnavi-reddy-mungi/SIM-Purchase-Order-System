package com.capgemini.telecom.ordersystem.exception;

public class InsufficientAmountException extends Exception{

    public InsufficientAmountException(String message){
        super(message);
    }
}
