package com.capgemini.telecom.ordersystem.mapper;

import com.capgemini.telecom.ordersystem.dto.CustomerRequestDTO;
import com.capgemini.telecom.ordersystem.dto.CustomerResponseDTO;
import com.capgemini.telecom.ordersystem.model.Customer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T15:48:05-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.4 (Eclipse Adoptium)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public CustomerResponseDTO modelToDto(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerResponseDTO.CustomerResponseDTOBuilder customerResponseDTO = CustomerResponseDTO.builder();

        customerResponseDTO.customerName( customerName( customer ) );
        customerResponseDTO.email( customer.getEmail() );
        customerResponseDTO.registered( customer.isRegistered() );
        customerResponseDTO.verified( customer.isVerified() );
        customerResponseDTO.activated( customer.isActivated() );

        return customerResponseDTO.build();
    }

    @Override
    public List<CustomerResponseDTO> modelsToDtos(List<Customer> customer) {
        if ( customer == null ) {
            return null;
        }

        List<CustomerResponseDTO> list = new ArrayList<CustomerResponseDTO>( customer.size() );
        for ( Customer customer1 : customer ) {
            list.add( modelToDto( customer1 ) );
        }

        return list;
    }

    @Override
    public Customer dtoToModel(CustomerRequestDTO customerDTO) {
        if ( customerDTO == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.id( customerDTO.getId() );
        customer.firstName( customerDTO.getFirstName() );
        customer.lastName( customerDTO.getLastName() );
        customer.email( customerDTO.getEmail() );
        customer.age( customerDTO.getAge() );

        return customer.build();
    }
}
