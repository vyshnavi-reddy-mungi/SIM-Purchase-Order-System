package com.capgemini.telecom.ordersystem.advice;

import com.capgemini.telecom.ordersystem.exception.*;
import com.capgemini.telecom.ordersystem.model.ApiError;
import com.capgemini.telecom.ordersystem.utils.ApiErrorCodes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.stereotype.Component;
import org.springframework.kafka.listener.ListenerExecutionFailedException;

import org.springframework.messaging.Message;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@Component
public class KafkaConsumerErrorHandler implements ConsumerAwareListenerErrorHandler {

    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaConsumerErrorHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {

        log.error("Error processing Kafka message: {}", message.getPayload());

        Throwable thrownException = exception.getCause();

        if (thrownException instanceof IllegalArgumentException ex) {
            ApiError apiError = new ApiError(ApiErrorCodes.HTTP_RESPONSE_INTERNAL_SERVER_ERROR, ex.getMessage());
            log.error("IllegalArgumentException occurred in Kafka listener for Error: {}", apiError);
        } else  if (thrownException instanceof SimCardNotFoundException ex) {
            ApiError apiError = new ApiError(ApiErrorCodes.SIM_CARD_NOT_FOUND, ex.getMessage());
            log.error("SimCardNotFoundException occurred in Kafka listener for Error: {}", apiError);
        } else if (thrownException instanceof PlanNotFoundException ex) {
            ApiError apiError = new ApiError(ApiErrorCodes.PLAN_NOT_FOUND, ex.getMessage());
            log.error("PlanNotFoundException occurred in Kafka listener for Error: {}", apiError);
        } else if (thrownException instanceof CustomerNotFoundException ex) {
            ApiError apiError = new ApiError(ApiErrorCodes.CUSTOMER_NOT_FOUND, ex.getMessage());
            log.error("CustomerNotFoundException occurred in Kafka listener for Error: {}", apiError);
        } else if (thrownException instanceof CustomerNotEligibleException ex) {
            ApiError apiError = new ApiError(ApiErrorCodes.CUSTOMER_NOT_ELIGIBLE, ex.getMessage());
            log.error("CustomerNotEligibleException occurred in Kafka listener for Error: {}", apiError);
        } else if (thrownException instanceof InsufficientAmountException ex) {
            ApiError apiError = new ApiError(ApiErrorCodes.INSUFFICIENT_AMOUNT, ex.getMessage());
            log.error("InsufficientAmountException occurred in Kafka listener for Error: {}", apiError);
        } else if (thrownException instanceof HttpClientErrorException.NotFound notFoundException) {
            String responseBody = notFoundException.getResponseBodyAsString();
            try {
                ApiError responseApiError = objectMapper.readValue(responseBody, ApiError.class);

                if (ApiErrorCodes.CUSTOMER_NOT_FOUND.equals(responseApiError.getErrorCode())) {
                    log.error("CustomerNotFoundException occurred due to 404 from plan-service: {}", responseApiError);
                } else if (ApiErrorCodes.PLAN_NOT_FOUND.equals(responseApiError.getErrorCode())) {
                    log.error("PlanNotFoundException occurred due to 404 from plan-service: {}", responseApiError);
                } else {
                    log.error("HttpClientErrorException$NotFound with unexpected API error: {}", responseApiError);
                }
            } catch (JsonProcessingException e) {
                log.warn("Could not parse JSON error response from plan-service: {}", responseBody, e);
                log.error("HttpClientErrorException$NotFound occurred: Status={}, Body={}", notFoundException.getStatusCode(), responseBody, notFoundException);
            }
        } else {
            log.error("An unexpected error occurred in Kafka listener for message: {}", message.getPayload(), thrownException);
        }

        return null;
    }
}