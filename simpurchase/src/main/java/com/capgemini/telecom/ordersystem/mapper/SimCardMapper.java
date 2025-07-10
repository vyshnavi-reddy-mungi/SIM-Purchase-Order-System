package com.capgemini.telecom.ordersystem.mapper;

import com.capgemini.telecom.ordersystem.dto.SimCardRequestDTO;
import com.capgemini.telecom.ordersystem.dto.SimCardResponseDTO;
import com.capgemini.telecom.ordersystem.model.SimCard;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SimCardMapper {

    SimCardResponseDTO modelToDto(SimCard simCard);
    List<SimCardResponseDTO> modelsToDtos(List<SimCard> simCards);
    SimCard dtoToModel(SimCardRequestDTO simCardDTO);
}
