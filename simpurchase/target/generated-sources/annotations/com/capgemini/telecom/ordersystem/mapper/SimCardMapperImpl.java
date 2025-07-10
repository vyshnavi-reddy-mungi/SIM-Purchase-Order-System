package com.capgemini.telecom.ordersystem.mapper;

import com.capgemini.telecom.ordersystem.dto.SimCardRequestDTO;
import com.capgemini.telecom.ordersystem.dto.SimCardResponseDTO;
import com.capgemini.telecom.ordersystem.model.SimCard;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T15:48:17-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.4 (Eclipse Adoptium)"
)
@Component
public class SimCardMapperImpl implements SimCardMapper {

    @Override
    public SimCardResponseDTO modelToDto(SimCard simCard) {
        if ( simCard == null ) {
            return null;
        }

        SimCardResponseDTO.SimCardResponseDTOBuilder simCardResponseDTO = SimCardResponseDTO.builder();

        simCardResponseDTO.simNumber( simCard.getSimNumber() );
        simCardResponseDTO.customerId( simCard.getCustomerId() );
        simCardResponseDTO.planId( simCard.getPlanId() );
        simCardResponseDTO.status( simCard.getStatus() );
        simCardResponseDTO.planStartDate( simCard.getPlanStartDate() );
        simCardResponseDTO.planEndDate( simCard.getPlanEndDate() );
        simCardResponseDTO.estimatedCost( simCard.getEstimatedCost() );

        return simCardResponseDTO.build();
    }

    @Override
    public List<SimCardResponseDTO> modelsToDtos(List<SimCard> simCards) {
        if ( simCards == null ) {
            return null;
        }

        List<SimCardResponseDTO> list = new ArrayList<SimCardResponseDTO>( simCards.size() );
        for ( SimCard simCard : simCards ) {
            list.add( modelToDto( simCard ) );
        }

        return list;
    }

    @Override
    public SimCard dtoToModel(SimCardRequestDTO simCardDTO) {
        if ( simCardDTO == null ) {
            return null;
        }

        SimCard.SimCardBuilder simCard = SimCard.builder();

        simCard.simNumber( simCardDTO.getSimNumber() );
        simCard.customerId( simCardDTO.getCustomerId() );
        simCard.planId( simCardDTO.getPlanId() );

        return simCard.build();
    }
}
