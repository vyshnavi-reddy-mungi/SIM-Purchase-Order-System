package com.capgemini.telecom.ordersystem.service;

import com.capgemini.telecom.ordersystem.dto.CustomerResponseDTO;
import com.capgemini.telecom.ordersystem.dto.PlanDTO;
import com.capgemini.telecom.ordersystem.exception.CustomerNotFoundException;
import com.capgemini.telecom.ordersystem.exception.PlanNotFoundException;
import com.capgemini.telecom.ordersystem.mapper.PlanMapper;
import com.capgemini.telecom.ordersystem.model.Plan;
import com.capgemini.telecom.ordersystem.repository.PlanRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.ParameterLabelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private PlanMapper planMapper;

    public Plan createPlan(PlanDTO planDTO) {
        log.info("planService:createPlan - request  {}", planDTO);
        Plan plan = planMapper.dtoToModel(planDTO);
        log.info("planService:createPlan - plan created");
        return planRepository.save(plan);
    }

    @Cacheable(value= "plans", key = "#planId")
    public PlanDTO getPlan(String planId) throws PlanNotFoundException {
        log.info("planService:getPlan - request  {}", planId);

        Plan plan =planRepository.findById(planId)
                .orElseThrow(() -> new PlanNotFoundException("Plan not found with id : " + planId));

        log.info("Plan info, {}",plan);
        return planMapper.modelToDto(plan);
    }

    @CachePut(value = "plans", key= "#planId")
    public PlanDTO updatePlan(String planId, PlanDTO planDTO) {
        log.info("planService:updatePlan - update plan {}", planDTO);
        Plan plan =planMapper.dtoToModel(planDTO);
        planRepository.save(plan);
        log.info("planService:updatePlan - plan updated");
        return planMapper.modelToDto(plan);
    }

    @CacheEvict(value = "plans", key= "#planId")
    public void deletePlan(String planId) throws PlanNotFoundException {
        log.info("planService:deletePlan - {}", planId);

        Plan plan = planRepository.findById(planId)
                .orElseThrow( () -> new PlanNotFoundException("Plan not found with id : " + planId));

        planRepository.deleteById(planId);
        log.info("Plan deleted, {}",plan);
    }
}
