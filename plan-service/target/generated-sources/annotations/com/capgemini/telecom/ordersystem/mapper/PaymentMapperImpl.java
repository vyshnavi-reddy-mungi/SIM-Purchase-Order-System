package com.capgemini.telecom.ordersystem.mapper;

import com.capgemini.telecom.ordersystem.dto.PaymentInfoDTO;
import com.capgemini.telecom.ordersystem.model.PaymentInfo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T15:48:34-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.4 (Eclipse Adoptium)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentInfoDTO modelToDto(PaymentInfo paymentInfo) {
        if ( paymentInfo == null ) {
            return null;
        }

        PaymentInfoDTO.PaymentInfoDTOBuilder paymentInfoDTO = PaymentInfoDTO.builder();

        paymentInfoDTO.accountNo( paymentInfo.getAccountNo() );
        paymentInfoDTO.cardType( paymentInfo.getCardType() );

        return paymentInfoDTO.build();
    }

    @Override
    public PaymentInfo dtoToModel(PaymentInfoDTO paymentInfoDTO) {
        if ( paymentInfoDTO == null ) {
            return null;
        }

        PaymentInfo.PaymentInfoBuilder paymentInfo = PaymentInfo.builder();

        paymentInfo.accountNo( paymentInfoDTO.getAccountNo() );
        paymentInfo.cardType( paymentInfoDTO.getCardType() );

        return paymentInfo.build();
    }
}
