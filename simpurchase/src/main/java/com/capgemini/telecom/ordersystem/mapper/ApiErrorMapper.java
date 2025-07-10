package com.capgemini.telecom.ordersystem.mapper;

import com.capgemini.telecom.ordersystem.dto.ApiErrorResponseDTO;
import com.capgemini.telecom.ordersystem.model.ApiError;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApiErrorMapper {

    ApiErrorResponseDTO modelToDto(ApiError apiError);
    ApiError dtoToModel(ApiErrorResponseDTO apiErrorResponseDTO);

}
