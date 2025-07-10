package com.capgemini.telecom.ordersystem.controller;

import com.capgemini.telecom.ordersystem.dto.PlanDTO;
import com.capgemini.telecom.ordersystem.enums.PlanPeriod;
import com.capgemini.telecom.ordersystem.enums.PlanType;
import com.capgemini.telecom.ordersystem.exception.PlanNotFoundException;
import com.capgemini.telecom.ordersystem.model.Plan;
import com.capgemini.telecom.ordersystem.service.PlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanControllerTest {

    @Mock
    private PlanService planService;

    @InjectMocks
    private PlanController planController;

    private PlanDTO planDTO;
    private Plan plan;

    @BeforeEach
    void setUp() {
        planDTO = PlanDTO.builder()
                .planName("Basic Plan")
                .dataPrice(10.0)
                .internetPrice(5.0)
                .planType(PlanType.DATA)
                .planPeriod(PlanPeriod.MONTHLY)
                .recurring(true)
                .build();

        plan = Plan.builder()
                .id("67d9eaea02dcae70b2f65b91")
                .planName("Basic Plan")
                .dataPrice(10.0)
                .internetPrice(5.0)
                .planType(PlanType.DATA)
                .planPeriod(PlanPeriod.MONTHLY)
                .recurring(true)
                .build();
    }

    @Test
    void createPlan_shouldReturnCreatedPlan() {
        when(planService.createPlan(planDTO)).thenReturn(plan);

        ResponseEntity<Plan> response = planController.createPlan(planDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(plan, response.getBody());
    }

    @Test
    void getPlan_shouldReturnPlanDTO() throws PlanNotFoundException {
        when(planService.getPlan("67d9eaea02dcae70b2f65b91")).thenReturn(planDTO);

        ResponseEntity<PlanDTO> response = planController.getPlan("67d9eaea02dcae70b2f65b91");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(planDTO, response.getBody());
    }

    @Test
    void getPlan_shouldThrowPlanNotFoundException() throws PlanNotFoundException {
        when(planService.getPlan("2")).thenThrow(new PlanNotFoundException("Plan not found"));

        try {
            planController.getPlan("2");
        } catch (PlanNotFoundException e) {
            assertEquals("Plan not found", e.getMessage());
        }

        verify(planService, times(1)).getPlan("2");
    }
}