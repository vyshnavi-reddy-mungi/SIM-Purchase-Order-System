package com.capgemini.telecom.ordersystem.mapper;

import com.capgemini.telecom.ordersystem.dto.PlanDTO;
import com.capgemini.telecom.ordersystem.model.Plan;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlanMapper {

    PlanDTO modelToDto(Plan plan);
    List<PlanDTO> modelsToDtos(List<Plan> plan);
    Plan dtoToModel(PlanDTO customerDTO);

}
