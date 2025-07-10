package com.capgemini.telecom.ordersystem.utils;

import lombok.Data;

@Data
public class ApiErrorCodes {

    public static final String CUSTOMER_NOT_FOUND = "1001";
    public static final String PLAN_NOT_FOUND = "1002";
    public static final String INVALID_ARGUMENT = "1003";
    public static final String CUSTOMER_NOT_ELIGIBLE = "1003";
    public static final String HTTP_RESPONSE_BAD_REQUEST = "400";
    public static final String HTTP_RESPONSE_NOT_FOUND = "404";
    public static final String HTTP_RESPONSE_INTERNAL_SERVER_ERROR = "500";

}
