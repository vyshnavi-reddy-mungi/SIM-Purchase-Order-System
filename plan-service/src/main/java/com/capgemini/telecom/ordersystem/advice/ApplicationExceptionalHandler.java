package com.capgemini.telecom.ordersystem.advice;

import com.capgemini.telecom.ordersystem.exception.*;
import com.capgemini.telecom.ordersystem.model.ApiError;
import com.capgemini.telecom.ordersystem.utils.ApiErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ApplicationExceptionalHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception ex) {
        ApiError apiError = new ApiError(ApiErrorCodes.HTTP_RESPONSE_INTERNAL_SERVER_ERROR, ex.getMessage());
        log.info("Exception occurred in {}: {}",
                ex.getStackTrace()[0].getClassName() + "." + ex.getStackTrace()[0].getMethodName(),
                apiError); // Log class/method
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException ex){
        ApiError apiError = new ApiError(ApiErrorCodes.HTTP_RESPONSE_BAD_REQUEST, ex.getMessage());
        log.info("IllegalArgumentException occurred in {}: {}", ex.getStackTrace()[0].getClassName() + "." + ex.getStackTrace()[0].getMethodName(),
                apiError); // Log class/method
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handleBusinessException(NoSuchElementException ex) {
        ApiError apiError = new ApiError(ApiErrorCodes.HTTP_RESPONSE_NOT_FOUND, ex.getMessage());
        log.info("NoSuchElementException occurred in {}: {}",
                ex.getStackTrace()[0].getClassName() + "." + ex.getStackTrace()[0].getMethodName(),
                apiError); // Log class/method
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleInValidArgument(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        String errorMessage = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        ApiError apiError = new ApiError(ApiErrorCodes.INVALID_ARGUMENT, errorMessage);
        log.info("Invalid argument in {}: {}",
                ex.getStackTrace()[0].getClassName() + "." + ex.getStackTrace()[0].getMethodName(),
                apiError);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PlanNotFoundException.class)
    public ResponseEntity<ApiError> handlePlanNotFoundException(PlanNotFoundException ex) {
        ApiError apiError = new ApiError(ApiErrorCodes.PLAN_NOT_FOUND, ex.getMessage());
        log.info("PlanNotFoundException occurred in {}: {}",
                ex.getStackTrace()[0].getClassName() + "." + ex.getStackTrace()[0].getMethodName(),
                apiError); // Log class/method
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SimCardNotFoundException.class)
    public ResponseEntity<ApiError> simcardNotFoundException(SimCardNotFoundException ex) {
        ApiError apiError = new ApiError(ApiErrorCodes.SIM_CARD_NOT_FOUND, ex.getMessage());
        log.info("SimCardNotFoundException occurred in {}: {}",
                ex.getStackTrace()[0].getClassName() + "." + ex.getStackTrace()[0].getMethodName(),
                apiError); // Log class/method
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerNotEligibleException.class)
    public ResponseEntity<ApiError> handlePlanNotFoundException(CustomerNotEligibleException ex) {
        ApiError apiError = new ApiError(ApiErrorCodes.PLAN_NOT_FOUND, ex.getMessage());
        log.info("CustomerNotEligibleException occurred in {}: {}",
                ex.getStackTrace()[0].getClassName() + "." + ex.getStackTrace()[0].getMethodName(),
                apiError); // Log class/method
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientAmountException.class)
    public ResponseEntity<ApiError> handleInsufficientAmountException(InsufficientAmountException ex) {
        ApiError apiError = new ApiError(ApiErrorCodes.INSUFFICIENT_AMOUNT, ex.getMessage());
        log.info("InsufficientAmountException occurred in {}: {}",
                ex.getStackTrace()[0].getClassName() + "." + ex.getStackTrace()[0].getMethodName(),
                apiError); // Log class/method
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

}
