package com.capgemini.telecom.ordersystem.controller;

import com.capgemini.telecom.ordersystem.dto.CustomerRequestDTO;
import com.capgemini.telecom.ordersystem.dto.CustomerResponseDTO;
import com.capgemini.telecom.ordersystem.exception.CustomerNotFoundException;
import com.capgemini.telecom.ordersystem.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private ThreadPoolTaskExecutor taskExecutor;

    @InjectMocks
    private CustomerController customerController;

    private CustomerRequestDTO customerRequestDTO;
    private CustomerResponseDTO customerResponseDTO;

    @BeforeEach
    void setUp() {
        customerRequestDTO = CustomerRequestDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .age(30)
                .build();

        customerResponseDTO = CustomerResponseDTO.builder()
                .customerName("John Doe")
                .email("john.doe@example.com")
                .registered(true)
                .verified(true)
                .activated(true)
                .build();
    }

    @Test
    void createCustomer_shouldReturnCreatedCustomer() throws Exception {
        when(customerService.createCustomer(customerRequestDTO)).thenReturn(customerResponseDTO);

        ResponseEntity<CustomerResponseDTO> response = customerController.createCustomer(customerRequestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customerResponseDTO, response.getBody());
    }

    @Test
    void getAllCustomers_shouldReturnListOfCustomers() {
        List<CustomerResponseDTO> responseDTOs = Arrays.asList(customerResponseDTO);
        when(customerService.getCustomers()).thenReturn(responseDTOs);

        ResponseEntity<List<CustomerResponseDTO>> response = customerController.getAllCustomers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTOs, response.getBody());
    }

    @Test
    void getCustomer_shouldReturnCustomer() throws CustomerNotFoundException {
        when(customerService.getCustomer("67ddf68a08ed9b12f30bcf9a")).thenReturn(customerResponseDTO);

        ResponseEntity<CustomerResponseDTO> response = customerController.getCustomer("67ddf68a08ed9b12f30bcf9a");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerResponseDTO, response.getBody());
    }

    @Test
    void deleteCustomer_shouldReturnNoContentDeleteCustomer() throws CustomerNotFoundException {
        ResponseEntity<Void> response = customerController.deleteCustomer("67ddf68a08ed9b12f30bcf9a");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(customerService, times(1)).deleteCustomer("67ddf68a08ed9b12f30bcf9a");
    }

    @Test
    void registerCustomer_shouldReturnRegisteredCustomer() throws CustomerNotFoundException {
        when(customerService.registerCustomer("67ddf68a08ed9b12f30bcf9a")).thenReturn(customerResponseDTO);

        ResponseEntity<CustomerResponseDTO> response = customerController.registerCustomer("67ddf68a08ed9b12f30bcf9a");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerResponseDTO, response.getBody());
    }

    @Test
    void verifyCustomer_shouldReturnVerifiedCustomer() throws CustomerNotFoundException {
        when(customerService.verifyCustomer("67ddf68a08ed9b12f30bcf9a")).thenReturn(customerResponseDTO);

        ResponseEntity<CustomerResponseDTO> response = customerController.verifyCustomer("67ddf68a08ed9b12f30bcf9a");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerResponseDTO, response.getBody());
    }

    @Test
    void activateCustomer_shouldReturnActivatedCustomer() throws CustomerNotFoundException {
        when(customerService.activateCustomer("67ddf68a08ed9b12f30bcf9a")).thenReturn(customerResponseDTO);

        ResponseEntity<CustomerResponseDTO> response = customerController.activateCustomer("67ddf68a08ed9b12f30bcf9a");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerResponseDTO, response.getBody());
    }

    @Test
    void updateCustomer_shouldReturnUpdatedCustomer() {
        when(customerService.updateCustomer(customerRequestDTO)).thenReturn(customerResponseDTO);

        ResponseEntity<CustomerResponseDTO> response = customerController.updateCustomer(customerRequestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customerResponseDTO, response.getBody());
    }
}