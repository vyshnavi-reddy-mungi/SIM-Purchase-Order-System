package com.capgemini.telecom.ordersystem.controller;

import com.capgemini.telecom.ordersystem.dto.PaymentInfoDTO;
import com.capgemini.telecom.ordersystem.dto.SimCardRequestDTO;
import com.capgemini.telecom.ordersystem.dto.SimCardResponseDTO;
import com.capgemini.telecom.ordersystem.service.SimCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimCardControllerTest {

    @Mock
    private SimCardService simCardService;

    @InjectMocks
    private SimCardController simCardController;

    private SimCardRequestDTO simCardRequestDTO;
    private SimCardResponseDTO simCardResponseDTO;

    @BeforeEach
    void setUp() {
        simCardRequestDTO = new SimCardRequestDTO();
        simCardRequestDTO.setSimNumber("1234567890");
        simCardRequestDTO.setCustomerId("cust123");
        simCardRequestDTO.setPlanId("plan123");
        PaymentInfoDTO paymentInfo = new PaymentInfoDTO();
        paymentInfo.setAccountNo("acc123");
        paymentInfo.setCardType("Visa");
        simCardRequestDTO.setPayment(paymentInfo);

        simCardResponseDTO = SimCardResponseDTO.builder()
                .message("Sim card purchased successfully.")
                .success(true)
                .build();
    }

    @Test
    void testPurchaseSimCard() throws Exception {
        when(simCardService.purchaseSimCard(any(SimCardRequestDTO.class))).thenReturn(simCardResponseDTO);

        ResponseEntity<SimCardResponseDTO> response = simCardController.purchaseSimCard(simCardRequestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(simCardResponseDTO, response.getBody());
    }

    @Test
    void testNoAvailableSimCards() {
        Exception exception = new Exception("No available sim cards");

        ResponseEntity<SimCardResponseDTO> response = simCardController.noAvailableSimCards(exception);

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Sim card purchase failed. No sim cards available at the moment. Please try again later.", response.getBody().getMessage());
    }


    @Test
    void testActivateSimCard() throws Exception {
        String simCardNumber = "1234567890";
        simCardResponseDTO.setMessage("SimCard to be activated..");
        when(simCardService.activateSimCard(simCardNumber)).thenReturn(simCardResponseDTO);

        ResponseEntity<SimCardResponseDTO> response = simCardController.activateSimCard(simCardNumber);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(simCardResponseDTO, response.getBody());
    }

    @Test
    void testSuspendSimCard() throws Exception {
        String simCardNumber = "1234567890";
        simCardResponseDTO.setMessage("SimCard to be suspended..");
        when(simCardService.suspendSimCard(simCardNumber)).thenReturn(simCardResponseDTO);

        ResponseEntity<SimCardResponseDTO> response = simCardController.suspendSimCard(simCardNumber);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(simCardResponseDTO, response.getBody());
    }

    @Test
    void testCancelSimCard() throws Exception {
        String simCardNumber = "1234567890";
        simCardResponseDTO.setMessage("SimCard to be cancelled..");
        when(simCardService.cancelSimCard(simCardNumber)).thenReturn(simCardResponseDTO);

        ResponseEntity<SimCardResponseDTO> response = simCardController.cancelSimCard(simCardNumber);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(simCardResponseDTO, response.getBody());
    }


}