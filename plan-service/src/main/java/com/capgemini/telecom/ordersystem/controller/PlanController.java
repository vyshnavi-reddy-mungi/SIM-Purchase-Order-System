package com.capgemini.telecom.ordersystem.controller;


import com.capgemini.telecom.ordersystem.dto.CustomerResponseDTO;
import com.capgemini.telecom.ordersystem.dto.PlanDTO;
import com.capgemini.telecom.ordersystem.exception.CustomerNotFoundException;
import com.capgemini.telecom.ordersystem.exception.PlanNotFoundException;
import com.capgemini.telecom.ordersystem.model.Plan;
import com.capgemini.telecom.ordersystem.service.CacheInspectionService;
import com.capgemini.telecom.ordersystem.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plans")
@Slf4j
@RefreshScope
@Tag(name = "Plan APIs")
public class PlanController {

    @Autowired
    private PlanService planService;

    @Autowired
    private CacheInspectionService cacheInspectionService;

    @Operation(summary = "Create a new plan")
    @PostMapping
    public ResponseEntity<Plan> createPlan(@RequestBody @Valid PlanDTO planDTO) {
        log.info("planController:createPlan - request  {}", planDTO);
        return new ResponseEntity<>(planService.createPlan(planDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all plans")
    @GetMapping("/{planId}")
    public ResponseEntity<PlanDTO> getPlan(@PathVariable String planId) throws PlanNotFoundException {
        log.info("planController:getPlan - get plan-{}", planId);
        PlanDTO plan = planService.getPlan(planId);

        return new ResponseEntity<>(plan, HttpStatus.OK);
    }

    @Operation(summary = "Update plan")
    @PutMapping("/update/plan/{planId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PlanDTO> updatePlan(@PathVariable String  planId,@RequestBody @Valid PlanDTO planDTO) {
        log.info("planController:updatePlan - update plan, {}", planDTO);
        return new ResponseEntity<>(planService.updatePlan(planId,planDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete plan")
    @DeleteMapping("/delete/{planId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String planId) throws PlanNotFoundException {
        log.info("planController:deletePlan - delete plan-{}", planId);
        planService.deletePlan(planId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get Cache data")
    @GetMapping("/cacheData")
    public void getCacheDate() {
        cacheInspectionService.printCacheContents("plans");
    }
}
