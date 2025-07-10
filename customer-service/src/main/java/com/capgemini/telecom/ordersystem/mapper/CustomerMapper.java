package com.capgemini.telecom.ordersystem.mapper;

import com.capgemini.telecom.ordersystem.dto.CustomerRequestDTO;
import com.capgemini.telecom.ordersystem.dto.CustomerResponseDTO;
import com.capgemini.telecom.ordersystem.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(source = ".", target = "customerName", qualifiedByName = "customerName")
    CustomerResponseDTO modelToDto(Customer customer);

    @Mapping(source = ".", target = "customerName", qualifiedByName = "customerName")
    List<CustomerResponseDTO> modelsToDtos(List<Customer> customer);

    Customer dtoToModel(CustomerRequestDTO customerDTO);

    @Named("customerName")
    default String customerName(Customer customer) {
        return customer.getFirstName() + " " + customer.getLastName();
    }
}
