package com.capgemini.telecom.ordersystem.service;

import com.capgemini.telecom.ordersystem.dto.CustomerRequestDTO;
import com.capgemini.telecom.ordersystem.dto.CustomerResponseDTO;
import com.capgemini.telecom.ordersystem.exception.CustomerCreateException;
import com.capgemini.telecom.ordersystem.exception.CustomerNotFoundException;
import com.capgemini.telecom.ordersystem.mapper.CustomerMapper;
import com.capgemini.telecom.ordersystem.model.Customer;
import com.capgemini.telecom.ordersystem.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    private CustomerRequestDTO customerRequestDTO;
    private Customer customer;
    private CustomerResponseDTO customerResponseDTO;

    @BeforeEach
    void setUp() {
        customerRequestDTO = CustomerRequestDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .age(23)
                .build();

        customer = Customer.builder()
                .id("67ddf68a08ed9b12f30bcf9a")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .age(23)
                .build();

        customerResponseDTO = CustomerResponseDTO.builder()
                .customerName("John Doe")
                .email("john.doe@gmail.com")
                .registered(false)
                .verified(false)
                .activated(false)
                .build();
    }

    @Test
    void createCustomer_shouldReturnCustomerResponseDTO() throws CustomerCreateException {
        when(customerMapper.dtoToModel(customerRequestDTO)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.modelToDto(customer)).thenReturn(customerResponseDTO);

        CustomerResponseDTO result = customerService.createCustomer(customerRequestDTO);

        assertNotNull(result);
        assertEquals(customerResponseDTO, result);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void getCustomers_shouldReturnListOfCustomerResponseDTO() {
        List<Customer> customers = Arrays.asList(customer);
        List<CustomerResponseDTO> customerResponseDTOs = Arrays.asList(customerResponseDTO);

        when(customerRepository.findAll()).thenReturn(customers);
        when(customerMapper.modelsToDtos(customers)).thenReturn(customerResponseDTOs);

        List<CustomerResponseDTO> result = customerService.getCustomers();

        assertNotNull(result);
        assertEquals(customerResponseDTOs, result);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getCustomer_shouldReturnCustomerResponseDTO() throws CustomerNotFoundException {
        when(customerRepository.findById("67ddf68a08ed9b12f30bcf9a")).thenReturn(Optional.of(customer));
        when(customerMapper.modelToDto(customer)).thenReturn(customerResponseDTO);

        CustomerResponseDTO result = customerService.getCustomer("67ddf68a08ed9b12f30bcf9a");

        assertNotNull(result);
        assertEquals(customerResponseDTO, result);
        verify(customerRepository, times(1)).findById("67ddf68a08ed9b12f30bcf9a");
    }

    @Test
    void getCustomer_shouldThrowCustomerNotFoundException() {
        when(customerRepository.findById("67ddf68a08ed9b12f30bcf9a")).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomer("67ddf68a08ed9b12f30bcf9a"));
        verify(customerRepository, times(1)).findById("67ddf68a08ed9b12f30bcf9a");
    }

    @Test
    void deleteCustomer_shouldDeleteCustomer() {
        when(customerRepository.findById("67ddf68a08ed9b12f30bcf9a")).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).deleteById("67ddf68a08ed9b12f30bcf9a");

        assertDoesNotThrow(() -> customerService.deleteCustomer("67ddf68a08ed9b12f30bcf9a"));
        verify(customerRepository, times(1)).deleteById("67ddf68a08ed9b12f30bcf9a");
    }

    @Test
    void deleteCustomer_shouldThrowCustomerNotFoundException() {
        when(customerRepository.findById("67ddf68a08ed9b12f30bcf9a")).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer("67ddf68a08ed9b12f30bcf9a"));
        verify(customerRepository, times(0)).deleteById("67ddf68a08ed9b12f30bcf9a");
    }

    @Test
    void registerCustomer_shouldReturnRegisteredCustomerResponseDTO() throws CustomerNotFoundException {
        customer.setRegistered(false);
        customerResponseDTO.setRegistered(true);

        when(customerRepository.findById("67ddf68a08ed9b12f30bcf9a")).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.modelToDto(customer)).thenReturn(customerResponseDTO);

        CustomerResponseDTO result = customerService.registerCustomer("67ddf68a08ed9b12f30bcf9a");

        assertNotNull(result);
        assertEquals(customerResponseDTO, result);
        assertTrue(result.isRegistered());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void verifyCustomer_shouldReturnVerifiedCustomerResponseDTO() throws CustomerNotFoundException {
        customer.setVerified(false);
        customerResponseDTO.setVerified(true);

        when(customerRepository.findById("67ddf68a08ed9b12f30bcf9a")).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.modelToDto(customer)).thenReturn(customerResponseDTO);

        CustomerResponseDTO result = customerService.verifyCustomer("67ddf68a08ed9b12f30bcf9a");

        assertNotNull(result);
        assertEquals(customerResponseDTO, result);
        assertTrue(result.isVerified());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void activateCustomer_shouldReturnActivatedCustomerResponseDTO() throws CustomerNotFoundException {
        customer.setActivated(false);
        customerResponseDTO.setActivated(true);

        when(customerRepository.findById("67ddf68a08ed9b12f30bcf9a")).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.modelToDto(customer)).thenReturn(customerResponseDTO);

        CustomerResponseDTO result = customerService.activateCustomer("67ddf68a08ed9b12f30bcf9a");

        assertNotNull(result);
        assertEquals(customerResponseDTO, result);
        assertTrue(result.isActivated());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void updateCustomer_shouldReturnUpdatedCustomerResponseDTO() {
        when(customerMapper.dtoToModel(customerRequestDTO)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.modelToDto(customer)).thenReturn(customerResponseDTO);

        CustomerResponseDTO result = customerService.updateCustomer(customerRequestDTO);

        assertNotNull(result);
        assertEquals(customerResponseDTO, result);
        verify(customerRepository, times(1)).save(customer);
    }
}
