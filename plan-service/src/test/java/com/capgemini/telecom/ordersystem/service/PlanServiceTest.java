package com.capgemini.telecom.ordersystem.service;

import com.capgemini.telecom.ordersystem.dto.PlanDTO;
import com.capgemini.telecom.ordersystem.enums.PlanPeriod;
import com.capgemini.telecom.ordersystem.enums.PlanType;
import com.capgemini.telecom.ordersystem.exception.PlanNotFoundException;
import com.capgemini.telecom.ordersystem.mapper.PlanMapper;
import com.capgemini.telecom.ordersystem.model.Plan;
import com.capgemini.telecom.ordersystem.repository.PlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanServiceTest {

    @Mock
    private PlanRepository planRepository;

    @Mock
    private PlanMapper planMapper;

    @InjectMocks
    private PlanService planService;

    private PlanDTO planDTO;
    private Plan plan;

    @BeforeEach
    void setUp() {
        planDTO = PlanDTO.builder()
                .planName("Basic Plan")
                .dataPrice(29.99)
                .internetPrice(19.99)
                .planType(PlanType.DATA)
                .planPeriod(PlanPeriod.MONTHLY)
                .recurring(true)
                .build();

        plan = Plan.builder()
                .id("67d9eaea02dcae70b2f65b91")
                .planName("Basic Plan")
                .dataPrice(29.99)
                .internetPrice(19.99)
                .planType(PlanType.DATA)
                .planPeriod(PlanPeriod.MONTHLY)
                .recurring(true)
                .build();
    }

    @Test
    void createPlan_shouldReturnCreatedPlan() {
        when(planMapper.dtoToModel(planDTO)).thenReturn(plan);
        when(planRepository.save(plan)).thenReturn(plan);

        Plan result = planService.createPlan(planDTO);

        assertNotNull(result);
        assertEquals(plan, result);
        verify(planRepository, times(1)).save(plan);
    }

    @Test
    void getPlan_shouldReturnPlanDTO() throws PlanNotFoundException {
        when(planRepository.findById("67d9eaea02dcae70b2f65b91")).thenReturn(Optional.of(plan));
        when(planMapper.modelToDto(plan)).thenReturn(planDTO);

        PlanDTO result = planService.getPlan("67d9eaea02dcae70b2f65b91");

        assertNotNull(result);
        assertEquals(planDTO, result);
        verify(planRepository, times(1)).findById("67d9eaea02dcae70b2f65b91");
    }

    @Test
    void getPlan_shouldThrowPlanNotFoundException() {
        when(planRepository.findById("67d9eaea02dcae70b2f65b91")).thenReturn(Optional.empty());

        assertThrows(PlanNotFoundException.class, () -> planService.getPlan("67d9eaea02dcae70b2f65b91"));
        verify(planRepository, times(1)).findById("67d9eaea02dcae70b2f65b91");
    }
}