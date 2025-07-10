package com.capgemini.telecom.ordersystem.controller;

import com.capgemini.telecom.ordersystem.dto.CustomerRequestDTO;
import com.capgemini.telecom.ordersystem.dto.CustomerResponseDTO;
import com.capgemini.telecom.ordersystem.dto.SimCardResponseDTO;
import com.capgemini.telecom.ordersystem.exception.CustomerCreateException;
import com.capgemini.telecom.ordersystem.exception.CustomerNotFoundException;
import com.capgemini.telecom.ordersystem.service.CustomerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/customers")
@Slf4j
@RefreshScope
@Tag(name = "Customer APIs")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private RestTemplate restTemplate;

    private static final String Base_URL = "http://sim-purchase/simcards/";

    private static final String CUSTOMER_SERVICE = "customerService";

    private int attempt = 1;

    @PostMapping("/create")
    @Operation(summary = "Create a new customer")
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody @Valid CustomerRequestDTO customerRequestDTO) throws CustomerCreateException {
        log.info("customerController:createCustomer - request  {}", customerRequestDTO);
        return new ResponseEntity<>(customerService.createCustomer(customerRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    @Operation(summary = "Get all customers")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        log.info("customerController:getAllCustomers - retrieving all customer");
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.OK);
    }

    @GetMapping("get/{customerId}")
    @Operation(summary = "Get customer by ID")
    public ResponseEntity<CustomerResponseDTO> getCustomer(@PathVariable String customerId) throws CustomerNotFoundException {
        log.info("customerController:getCustomer - get customer-{}", customerId);
        return new ResponseEntity<>(customerService.getCustomer(customerId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{customerId}")
    @Operation(summary = "Delete customer by ID")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String customerId) throws CustomerNotFoundException {
        log.info("customerController:deleteCustomer - delete customer-{}", customerId);
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/register/{customerId}")
    @Operation(summary = "Register customer by ID")
    public ResponseEntity<CustomerResponseDTO> registerCustomer(@PathVariable String customerId) throws CustomerNotFoundException {
        log.info("customerController:registerCustomer - register customer-{}", customerId);
        return new ResponseEntity<>(customerService.registerCustomer(customerId), HttpStatus.OK);
    }

    @PutMapping("/verify/{customerId}")
    @Operation(summary = "Verify customer by ID")
    public ResponseEntity<CustomerResponseDTO> verifyCustomer(@PathVariable String customerId) throws CustomerNotFoundException {
        log.info("customerController:verifyCustomer - verify customer- {}", customerId);
        return new ResponseEntity<>(customerService.verifyCustomer(customerId), HttpStatus.OK);
    }

    @PutMapping("/activate/{customerId}")
    @Operation(summary = "Activate customer by ID")
    public ResponseEntity<CustomerResponseDTO> activateCustomer(@PathVariable String customerId) throws CustomerNotFoundException {
        log.info("customerController:activateCustomer - activate customer-{}", customerId);
        return new ResponseEntity<>(customerService.activateCustomer(customerId), HttpStatus.OK);
    }

    @PutMapping("/update/customer")
    @Operation(summary = "Update customer")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@RequestBody CustomerRequestDTO customerDTO) {
        log.info("customerController:updateCustomer - update customer, {}", customerDTO);
        return new ResponseEntity<>(customerService.updateCustomer(customerDTO), HttpStatus.CREATED);
    }

    @GetMapping("/simcards/{customerId}")
    @Operation(summary = "Get all sim cards by customer ID")
//    @CircuitBreaker(name = CUSTOMER_SERVICE, fallbackMethod = "noAvailableSimCards")
    @Retry(name = CUSTOMER_SERVICE, fallbackMethod = "noAvailableSimCards")
    public ResponseEntity<List<SimCardResponseDTO>> getAllSimCardsByCustomerId(@PathVariable String customerId) {
        log.info("customerController:getAllSimCardsByCustomerId - customerId {}", customerId);

        String url = Base_URL + customerId;
        System.out.println("retry method called " + attempt++ + " times at" + LocalDateTime.now());

        return new ResponseEntity<>(restTemplate.getForObject(url, List.class), HttpStatus.OK);
    }

    public ResponseEntity<List<SimCardResponseDTO>> noAvailableSimCards(Exception e) {
        log.error("customerController:noAvailableSimCards - Circuit breaker triggered. No available sim cards. Request: {}",e.getMessage());

        List<SimCardResponseDTO> fallbackResponse = Collections.singletonList(SimCardResponseDTO.builder()
                .message("No sim cards available at the moment. Please try again later.")
                .success(false)
                .build());

        return new ResponseEntity<>(fallbackResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
