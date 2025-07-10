package com.capgemini.telecom.ordersystem.kafka;

import com.capgemini.telecom.ordersystem.dto.*;
import com.capgemini.telecom.ordersystem.enums.PlanPeriod;
import com.capgemini.telecom.ordersystem.enums.PlanType;
import com.capgemini.telecom.ordersystem.enums.SimCardStatus;
import com.capgemini.telecom.ordersystem.exception.*;
import com.capgemini.telecom.ordersystem.mapper.PaymentMapper;
import com.capgemini.telecom.ordersystem.mapper.PlanMapper;
import com.capgemini.telecom.ordersystem.mapper.SimCardMapper;
import com.capgemini.telecom.ordersystem.model.PaymentInfo;
import com.capgemini.telecom.ordersystem.model.Plan;
import com.capgemini.telecom.ordersystem.model.SimCard;
import com.capgemini.telecom.ordersystem.repository.PaymentInfoRepository;
import com.capgemini.telecom.ordersystem.repository.PlanRepository;
import com.capgemini.telecom.ordersystem.repository.SimCardRepository;
import com.capgemini.telecom.ordersystem.utils.PaymentUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class SimCardConsumer {

    private static final String GET_CUSTOMERS = "http://customer-service/customers/get/";
    private static final String CUSTOMER_NOT_FOUND = "Customer not found with id: ";
    private static final String PLAN_NOT_FOUND = "Plan not found with id: ";
    private static final String SIM_CARD_NOT_FOUND = "Sim card not found with id: ";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SimCardMapper simCardMapper;
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private SimCardRepository simCardRepository;
    @Autowired
    private PaymentInfoRepository paymentInfoRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private PlanMapper planMapper;
    @Autowired
    private WebClient webClient;

    //    @Transactional(rollbackFor = InsufficientAmountException.class)
//@RetryableTopic(kafkaTemplate = "kafkaTemplate" , attempts = "4",
//        backoff = @Backoff(delay = 2000, multiplier = 2), include = {Throwable.class})
//@KafkaListener(topics = "${spring.kafka.topic.purchase.name}",
//        groupId = "${spring.kafka.consumer.group-id}")
    @Transactional(rollbackFor = InsufficientAmountException.class)
    @RetryableTopic(kafkaTemplate = "kafkaTemplate", attempts = "4",
            backoff = @Backoff(delay = 2000, multiplier = 2), include = {Throwable.class})
    @KafkaListener(topics = "${spring.kafka.topic.purchase.name}",
            groupId = "${spring.kafka.consumer.group-id}",
            errorHandler = "kafkaConsumerErrorHandler")
    public SimPurchaseAcknowledgement consumeSimPurchase(SimCardEvent event) throws CustomerNotFoundException, CustomerNotEligibleException, PlanNotFoundException, InsufficientAmountException {

        log.info(String.format("Order event received in plan service => %s", event.toString()));

        SimCardRequestDTO simCardDTO = event.getOrder();
        CustomerResponseDTO customerDTO = restTemplate.getForObject(GET_CUSTOMERS + simCardDTO.getCustomerId(), CustomerResponseDTO.class);


//        Mono<CustomerResponseDTO> customerDTOMono = webClient.get()
//                .uri(GET_CUSTOMERS + simCardDTO.getCustomerId())
//                .retrieve()
//                .bodyToMono(CustomerResponseDTO.class);

//        CustomerResponseDTO customerDTO = customerDTOMono.block();
        Optional.ofNullable(customerDTO)
                .orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND + simCardDTO.getCustomerId()));

        if (!customerDTO.isRegistered() || !customerDTO.isVerified() || !customerDTO.isActivated()) {
            log.error("Customer is not eligible for sim purchase. Customer ID: {}", simCardDTO.getCustomerId());
            throw new CustomerNotEligibleException("Customer is not eligible for sim purchase");
        }

        Plan plan = planRepository.findById(simCardDTO.getPlanId())
                .orElseThrow(() -> new PlanNotFoundException(PLAN_NOT_FOUND + simCardDTO.getPlanId()));
        PlanDTO planDTO = planMapper.modelToDto(plan);

        double estimatedCost = calculateEstimatedCost(planDTO);

        SimCard simCard = simCardMapper.dtoToModel(simCardDTO);
        simCard.setEstimatedCost(estimatedCost);
        simCard.setStatus(SimCardStatus.PENDING);
        LocalDateTime nowCST = LocalDateTime.now(ZoneId.of("America/Chicago"));
        simCard.setPurchaseDate(nowCST);
        simCardRepository.save(simCard);

        try {
            PaymentUtils.validateCreditLimit(estimatedCost);
        } catch (InsufficientAmountException e) {
            log.error("Insufficient amount exception occurred: {}", e.getMessage());
            throw e;
        }
        PaymentInfo paymentInfo = paymentMapper.dtoToModel(event.getOrder().getPayment());
        paymentInfo.setCustomerId(event.getOrder().getCustomerId());
        paymentInfo.setAmount(estimatedCost);
        paymentInfoRepository.save(paymentInfo);

        log.info("SimCardConsumer:consumeSimPurchase - Sim card purchase initiated. Sim Card ID: {}", simCard.getSimNumber());

        return new SimPurchaseAcknowledgement("SUCCESS", estimatedCost, UUID.randomUUID().toString().split("-")[0], event.getOrder());

    }

    @DltHandler
    public void listenDLT(SimCardEvent event) {
        log.info("SimCardConsumer:listenDLT - event received => {}", event.toString());
    }

    @KafkaListener(topics = "${spring.kafka.topic.activate.name}",
            groupId = "${spring.kafka.consumer.group-id}",
            errorHandler = "kafkaConsumerErrorHandler")
    public void consumeSimActivate(SimCardEvent event) throws SimCardNotFoundException, PlanNotFoundException {
        log.info(String.format("Order event received for activation in plan service => %s", event.toString()));

        SimCardRequestDTO simCardDTO = event.getOrder();

        SimCard simCard = simCardRepository.findBySimNumber(simCardDTO.getSimNumber())
                .orElseThrow(() -> new SimCardNotFoundException(SIM_CARD_NOT_FOUND + simCardDTO.getSimNumber()));

        simCard.setStatus(SimCardStatus.ACTIVE);

        Plan plan = planRepository.findById(simCard.getPlanId())
                .orElseThrow(() -> new PlanNotFoundException(PLAN_NOT_FOUND + simCard.getPlanId()));
        PlanDTO planDTO = planMapper.modelToDto(plan);

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = calculateEndDate(startDate, planDTO.getPlanPeriod());
        simCard.setPlanStartDate(startDate);
        simCard.setPlanEndDate(endDate);

        simCardRepository.save(simCard);
        log.info("SimCardConsumer:consumeSimActivate saved - {}", simCardDTO.getSimNumber());

    }


    @KafkaListener(topics = "${spring.kafka.topic.suspend.name}",
            groupId = "${spring.kafka.consumer.group-id}",
            errorHandler = "kafkaConsumerErrorHandler")
    public void consumeSimSuspend(SimCardEvent event) throws SimCardNotFoundException, PlanNotFoundException {
        log.info(String.format("Order event received for suspension in plan service => %s", event.toString()));

        SimCardRequestDTO simCardDTO = event.getOrder();

        SimCard simCard = simCardRepository.findBySimNumber(simCardDTO.getSimNumber())
                .orElseThrow(() -> new SimCardNotFoundException(SIM_CARD_NOT_FOUND + simCardDTO.getSimNumber()));

        simCard.setStatus(SimCardStatus.SUSPENDED);

        Plan plan = planRepository.findById(simCard.getPlanId())
                .orElseThrow(() -> new PlanNotFoundException(PLAN_NOT_FOUND + simCard.getPlanId()));
        PlanDTO planDTO = planMapper.modelToDto(plan);

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = calculateEndDate(startDate, planDTO.getPlanPeriod());
        simCard.setPlanStartDate(startDate);
        simCard.setPlanEndDate(endDate);

        simCardRepository.save(simCard);
        log.info("SimCardConsumer:consumeSimSuspend saved - {}", simCardDTO.getSimNumber());
    }

    @KafkaListener(topics = "${spring.kafka.topic.cancel.name}",
            groupId = "${spring.kafka.consumer.group-id}",
            errorHandler = "kafkaConsumerErrorHandler")
    public void consumeSimCancel(SimCardEvent event) throws SimCardNotFoundException, PlanNotFoundException {
        log.info(String.format("Order event received for cancellation in plan service => %s", event.toString()));

        SimCardRequestDTO simCardDTO = event.getOrder();

        SimCard simCard = simCardRepository.findBySimNumber(simCardDTO.getSimNumber())
                .orElseThrow(() -> new SimCardNotFoundException(SIM_CARD_NOT_FOUND + simCardDTO.getSimNumber()));

        simCard.setStatus(SimCardStatus.CANCELLED);

        Plan plan = planRepository.findById(simCard.getPlanId())
                .orElseThrow(() -> new PlanNotFoundException(PLAN_NOT_FOUND + simCard.getPlanId()));
        PlanDTO planDTO = planMapper.modelToDto(plan);

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = calculateEndDate(startDate, planDTO.getPlanPeriod());
        simCard.setPlanStartDate(startDate);
        simCard.setPlanEndDate(endDate);

        simCardRepository.save(simCard);
        log.info("SimCardConsumer:consumeSimCancel saved - {}", simCardDTO.getSimNumber());
    }

    public double calculateEstimatedCost(PlanDTO plan) {

        double totalCost = 0.0;
        double monthlyCost = 0.0;

        if (plan.getPlanType() == PlanType.DATA) {
            monthlyCost = plan.getDataPrice();
        } else if (plan.getPlanType() == PlanType.INTERNET) {
            monthlyCost = plan.getInternetPrice();
        } else if (plan.getPlanType() == PlanType.DATA_AND_INTERNET) {
            monthlyCost = plan.getDataPrice() + plan.getInternetPrice();
        }

        if (plan.getPlanPeriod() == PlanPeriod.MONTHLY) {
            totalCost = monthlyCost;
        } else if (plan.getPlanPeriod() == PlanPeriod.YEARLY) {
            totalCost = monthlyCost * 12; // 12 months in a year
        }
        return totalCost;
    }

    private LocalDate calculateEndDate(LocalDate startDate, PlanPeriod planPeriod) {
        if (planPeriod == PlanPeriod.MONTHLY) {
            return startDate.plus(Period.ofMonths(1));
        } else if (planPeriod == PlanPeriod.YEARLY) {
            return startDate.plus(Period.ofYears(1));
        }
        return startDate;
    }

}
