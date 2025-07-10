package com.capgemini.telecom.ordersystem.mapper;

import com.capgemini.telecom.ordersystem.dto.PaymentInfoDTO;
import com.capgemini.telecom.ordersystem.model.PaymentInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentInfoDTO modelToDto(PaymentInfo paymentInfo);
    PaymentInfo dtoToModel(PaymentInfoDTO paymentInfoDTO);
}
