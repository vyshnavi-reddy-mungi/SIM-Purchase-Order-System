package com.capgemini.telecom.ordersystem.service;


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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class SimCardService {

    @Autowired
    private SimCardRepository simCardRepository;

    @Autowired
    private SimCardMapper simCardMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SimCardProducer simCardProducer;

    private static final String STATUS_PENDING = "PENDING";

    public SimCardResponseDTO purchaseSimCard(SimCardRequestDTO simCardDTO) throws PlanNotFoundException, CustomerNotFoundException, CustomerNotEligibleException {
        log.info("simCardService:purchaseSimCard - request  {}", simCardDTO);

        SimCardEvent simCardEvent = new SimCardEvent();
        simCardEvent.setStatus(STATUS_PENDING);
        simCardEvent.setMessage("sim card status is in pending state");
        simCardEvent.setOrder(simCardDTO);

        simCardProducer.sendPurchaseMessage(simCardEvent);

        SimCardResponseDTO simCardResponseDTO = new SimCardResponseDTO();
        simCardResponseDTO.setMessage("SimCard order placed successfully...");
        simCardResponseDTO.setSuccess(true);
        return simCardResponseDTO;
    }


    public SimCardResponseDTO activateSimCard(String simCardNumber) throws SimCardNotFoundException, PlanNotFoundException {
        log.info("simCardService:activateSimCard - {}", simCardNumber);

        SimCardRequestDTO simCardDTO = new SimCardRequestDTO();
        simCardDTO.setSimNumber(simCardNumber);

        SimCardEvent simCardEvent = new SimCardEvent();
        simCardEvent.setStatus(STATUS_PENDING);
        simCardEvent.setMessage("sim card status is about to activate");
        simCardEvent.setOrder(simCardDTO);

        simCardProducer.sendActivateMessage(simCardEvent);

        SimCardResponseDTO simCardResponseDTO = new SimCardResponseDTO();
        simCardResponseDTO.setMessage("SimCard to be activated..");
        simCardResponseDTO.setSuccess(true);
        return simCardResponseDTO;
    }

    public SimCardResponseDTO suspendSimCard(String simCardNumber) throws SimCardNotFoundException, PlanNotFoundException {
        log.info("simCardService:suspendSimCard - {}", simCardNumber);
        SimCardRequestDTO simCardDTO = new SimCardRequestDTO();
        simCardDTO.setSimNumber(simCardNumber);

        SimCardEvent simCardEvent = new SimCardEvent();
        simCardEvent.setStatus(STATUS_PENDING);
        simCardEvent.setMessage("sim card status is about to suspend");
        simCardEvent.setOrder(simCardDTO);

        simCardProducer.sendSuspendMessage(simCardEvent);

        SimCardResponseDTO simCardResponseDTO = new SimCardResponseDTO();
        simCardResponseDTO.setMessage("SimCard to be suspended..");
        simCardResponseDTO.setSuccess(true);
        return simCardResponseDTO;
    }

    public SimCardResponseDTO cancelSimCard(String simCardNumber) throws SimCardNotFoundException, PlanNotFoundException {
        log.info("simCardService:cancelSimCard - {}", simCardNumber);
        SimCardRequestDTO simCardDTO = new SimCardRequestDTO();
        simCardDTO.setSimNumber(simCardNumber);

        SimCardEvent simCardEvent = new SimCardEvent();
        simCardEvent.setStatus(STATUS_PENDING);
        simCardEvent.setMessage("sim card status is about to cancel");
        simCardEvent.setOrder(simCardDTO);

        simCardProducer.sendCancelMessage(simCardEvent);

        SimCardResponseDTO simCardResponseDTO = new SimCardResponseDTO();
        simCardResponseDTO.setMessage("SimCard to be cancelled..");
        simCardResponseDTO.setSuccess(true);
        return simCardResponseDTO;
    }

    public List<SimCardResponseDTO> getAllSimCardsByCustomerId(String customerId) {
        log.info("simCardService:getAllSimCardsByCustomerId - customerId {}", customerId);

        return simCardMapper.modelsToDtos(simCardRepository.findByCustomerId(customerId));
    }
}