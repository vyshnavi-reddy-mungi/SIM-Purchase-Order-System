package com.capgemini.telecom.ordersystem.mapper;

import com.capgemini.telecom.ordersystem.dto.ApiErrorResponseDTO;
import com.capgemini.telecom.ordersystem.model.ApiError;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T15:48:34-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.4 (Eclipse Adoptium)"
)
@Component
public class ApiErrorMapperImpl implements ApiErrorMapper {

    @Override
    public ApiErrorResponseDTO modelToDto(ApiError apiError) {
        if ( apiError == null ) {
            return null;
        }

        ApiErrorResponseDTO.ApiErrorResponseDTOBuilder apiErrorResponseDTO = ApiErrorResponseDTO.builder();

        apiErrorResponseDTO.errorCode( apiError.getErrorCode() );
        apiErrorResponseDTO.errorMessage( apiError.getErrorMessage() );

        return apiErrorResponseDTO.build();
    }

    @Override
    public ApiError dtoToModel(ApiErrorResponseDTO apiErrorResponseDTO) {
        if ( apiErrorResponseDTO == null ) {
            return null;
        }

        ApiError apiError = new ApiError();

        apiError.setErrorCode( apiErrorResponseDTO.getErrorCode() );
        apiError.setErrorMessage( apiErrorResponseDTO.getErrorMessage() );

        return apiError;
    }
}
