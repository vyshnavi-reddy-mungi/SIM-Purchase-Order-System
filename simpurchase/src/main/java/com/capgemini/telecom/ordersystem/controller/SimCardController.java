package com.capgemini.telecom.ordersystem.controller;


import com.capgemini.telecom.ordersystem.dto.SimCardRequestDTO;
import com.capgemini.telecom.ordersystem.dto.SimCardResponseDTO;
import com.capgemini.telecom.ordersystem.exception.CustomerNotEligibleException;
import com.capgemini.telecom.ordersystem.exception.CustomerNotFoundException;
import com.capgemini.telecom.ordersystem.exception.PlanNotFoundException;
import com.capgemini.telecom.ordersystem.exception.SimCardNotFoundException;
import com.capgemini.telecom.ordersystem.service.SimCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/simcards")
@Slf4j
@RefreshScope
@Tag(name = "Simcard APIs")
public class SimCardController {

    @Autowired
    private SimCardService simCardService;

    public static final String SIM_SERVICE = "simService";

    private int attempt = 1;

    @PostMapping("/purchase")
//  @CircuitBreaker(name = SIM_SERVICE, fallbackMethod = "noAvailableSimCards")
//  @Retry(name = SIM_SERVICE, fallbackMethod = "noAvailableSimCards")
    @Operation(summary = "Purchase a new sim card")
    public ResponseEntity<SimCardResponseDTO> purchaseSimCard(@RequestBody @Valid SimCardRequestDTO simCardDTO) throws CustomerNotFoundException, PlanNotFoundException, CustomerNotEligibleException {
        log.info("simCardController:purchaseSimCard - request  {}", simCardDTO);
        log.info("retry method called "+attempt++ + " times at" + new Date());
        return new ResponseEntity<>(simCardService.purchaseSimCard(simCardDTO), HttpStatus.CREATED);
    }

    public ResponseEntity<SimCardResponseDTO> noAvailableSimCards(Exception e) {
        log.error("simCardController:noAvailableSimCards - Circuit breaker triggered. No available sim cards. Request: {}",e.getMessage());

        SimCardResponseDTO fallbackResponse = SimCardResponseDTO.builder()
                .message("Sim card purchase failed. No sim cards available at the moment. Please try again later.")
                .success(false)
                .build();

        return new ResponseEntity<>(fallbackResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Operation(summary = "Activate sim card details by sim card number")
    @PutMapping("/activate/{simCardNumber}")
    public ResponseEntity<SimCardResponseDTO> activateSimCard(@PathVariable String simCardNumber) throws SimCardNotFoundException, PlanNotFoundException {
        log.info("simCardController:activateSimCard - activate sim card-{}", simCardNumber);
        return new ResponseEntity<>(simCardService.activateSimCard(simCardNumber), HttpStatus.OK);
    }

    @Operation(summary = "Suspend sim card details by sim card number")
    @PutMapping("/suspend/{simCardNumber}")
    public ResponseEntity<SimCardResponseDTO> suspendSimCard(@PathVariable String simCardNumber) throws SimCardNotFoundException, PlanNotFoundException {
        log.info("simCardController:suspendSimCard - suspend sim card-{}", simCardNumber);
        return new ResponseEntity<>(simCardService.suspendSimCard(simCardNumber), HttpStatus.OK);
    }

    @Operation(summary = "Cancel sim card details by sim card number")
    @PutMapping("/cancel/{simCardNumber}")
    public ResponseEntity<SimCardResponseDTO> cancelSimCard(@PathVariable String simCardNumber) throws SimCardNotFoundException, PlanNotFoundException {
        log.info("simCardController:cancelSimCard - cancel sim card-{}", simCardNumber);
        return new ResponseEntity<>(simCardService.cancelSimCard(simCardNumber), HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    @Operation(summary = "Get all sim cards by customer ID")
    public ResponseEntity<List<SimCardResponseDTO>> getAllSimCardsByCustomerId(@PathVariable String customerId) {
        log.info("simCardController:getAllSimCardsByCustomerId - customerId {}", customerId);
        return new ResponseEntity<>(simCardService.getAllSimCardsByCustomerId(customerId), HttpStatus.OK);
    }
}
