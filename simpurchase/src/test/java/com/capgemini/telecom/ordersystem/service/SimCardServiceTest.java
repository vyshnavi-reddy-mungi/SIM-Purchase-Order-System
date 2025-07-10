package com.capgemini.telecom.ordersystem.service;

import com.capgemini.telecom.ordersystem.dto.PaymentInfoDTO;
import com.capgemini.telecom.ordersystem.dto.SimCardEvent;
import com.capgemini.telecom.ordersystem.dto.SimCardRequestDTO;
import com.capgemini.telecom.ordersystem.dto.SimCardResponseDTO;
import com.capgemini.telecom.ordersystem.exception.CustomerNotEligibleException;
import com.capgemini.telecom.ordersystem.exception.CustomerNotFoundException;
import com.capgemini.telecom.ordersystem.exception.PlanNotFoundException;
import com.capgemini.telecom.ordersystem.exception.SimCardNotFoundException;
import com.capgemini.telecom.ordersystem.kafka.SimCardProducer;
import com.capgemini.telecom.ordersystem.mapper.SimCardMapper;
import com.capgemini.telecom.ordersystem.repository.SimCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimCardServiceTest {

    @Mock
    private SimCardRepository simCardRepository;

    @Mock
    private SimCardMapper simCardMapper;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private SimCardProducer simCardProducer;

    @InjectMocks
    private SimCardService simCardService;

    private SimCardRequestDTO simCardRequestDTO;
    private SimCardResponseDTO simCardResponseDTO;
    private  SimCardEvent simCardEvent;

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

        simCardEvent = new SimCardEvent();
        simCardEvent.setStatus("PENDING");
        simCardEvent.setMessage("sim card status is in pending state");
        simCardEvent.setOrder(simCardRequestDTO);

        simCardResponseDTO = SimCardResponseDTO.builder()
                .message("SimCard order placed successfully...")
                .success(true)
                .build();
    }


    @Test
    void purchaseSimCard_success() throws CustomerNotFoundException, PlanNotFoundException, CustomerNotEligibleException {
        SimCardResponseDTO response = simCardService.purchaseSimCard(simCardRequestDTO);

        assertEquals("SimCard order placed successfully...", response.getMessage());
        assertEquals(true, response.isSuccess());

        verify(simCardProducer, times(1)).sendPurchaseMessage(any(SimCardEvent.class));
    }

    @Test
    void activateSimCard_success() throws SimCardNotFoundException, PlanNotFoundException {
        String simCardNumber = "1234567890";

        SimCardResponseDTO response = simCardService.activateSimCard(simCardNumber);

        assertEquals("SimCard to be activated..", response.getMessage());
        assertEquals(true, response.isSuccess());

        verify(simCardProducer, times(1)).sendActivateMessage(any(SimCardEvent.class));
    }

    @Test
    void suspendSimCard_success() throws SimCardNotFoundException, PlanNotFoundException {
        String simCardNumber = "1234567890";

        SimCardResponseDTO response = simCardService.suspendSimCard(simCardNumber);

        assertEquals("SimCard to be suspended..", response.getMessage());
        assertEquals(true, response.isSuccess());

        verify(simCardProducer, times(1)).sendSuspendMessage(any(SimCardEvent.class));
    }

    @Test
    void cancelSimCard_success() throws SimCardNotFoundException, PlanNotFoundException {
        String simCardNumber = "1234567890";

        SimCardResponseDTO response = simCardService.cancelSimCard(simCardNumber);

        assertEquals("SimCard to be cancelled..", response.getMessage());
        assertEquals(true, response.isSuccess());

        verify(simCardProducer, times(1)).sendCancelMessage(any(SimCardEvent.class));
    }
}