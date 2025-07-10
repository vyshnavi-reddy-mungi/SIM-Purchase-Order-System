package com.capgemini.telecom.ordersystem.service;

import com.capgemini.telecom.ordersystem.dto.CustomerRequestDTO;
import com.capgemini.telecom.ordersystem.dto.CustomerResponseDTO;
import com.capgemini.telecom.ordersystem.exception.CustomerCreateException;
import com.capgemini.telecom.ordersystem.exception.CustomerNotFoundException;
import com.capgemini.telecom.ordersystem.mapper.CustomerMapper;
import com.capgemini.telecom.ordersystem.model.Customer;
import com.capgemini.telecom.ordersystem.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    private static final String CUSTOMER_NOT_FOUND  = "Customer not found with id : ";

    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerDTO) throws CustomerCreateException {
        log.info("customerService:createCustomer - request  {}", customerDTO);
        try {
            Customer customer = customerMapper.dtoToModel(customerDTO);
            customerRepository.save(customer);
            log.info("customerService:createCustomer - customer created");
            return customerMapper.modelToDto(customer);
        } catch (Exception e) {
            log.error("Error creating customer, retrying...", e);
            throw new CustomerCreateException("Failed to create customer");
        }
    }

    public List<CustomerResponseDTO> getCustomers() {
        log.info("customerService:getCustomers - get all customers");
        return customerMapper.modelsToDtos(customerRepository.findAll());
    }

    public CustomerResponseDTO getCustomer(String customerId) throws CustomerNotFoundException{
        Customer customer =customerRepository.findById(customerId)
                            .orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND  + customerId));

        log.info("Customer info, {}",customer);
        return customerMapper.modelToDto(customer);
    }

    public void deleteCustomer(String customerId) throws CustomerNotFoundException {
        log.info("customerService:deleteCustomer - {}", customerId);

        Customer customer = customerRepository.findById(customerId)
                            .orElseThrow( () -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND  + customerId));

        customerRepository.deleteById(customerId);
        log.info("Customer deleted, {}",customer);
    }

    public CustomerResponseDTO registerCustomer(String customerId) throws CustomerNotFoundException {
        log.info("customerService:registerCustomer - {}", customerId);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND  + customerId));

        customer.setRegistered(true);
        customerRepository.save(customer);

        log.info("customerService:registerCustomer saved - {}", customerId);
        return customerMapper.modelToDto(customer);
    }

    public CustomerResponseDTO verifyCustomer(String customerId) throws CustomerNotFoundException {
        log.info("customerService:verifyCustomer - {}", customerId);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND + customerId));

        customer.setVerified(true);
        customerRepository.save(customer);

        log.info("customerService:verifyCustomer saved - {}", customerId);
        return customerMapper.modelToDto(customer);
    }

    public CustomerResponseDTO activateCustomer(String customerId) throws CustomerNotFoundException {
        log.info("customerService:activateCustomer - {}", customerId);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND  + customerId));

        customer.setActivated(true);
        customerRepository.save(customer);

        log.info("customerService:activateCustomer saved - {}", customerId);
        return customerMapper.modelToDto(customer);
    }

    public CustomerResponseDTO updateCustomer(CustomerRequestDTO customerDTO) {
        log.info("customerService:updateCustomer - update customer {}", customerDTO);
        Customer customer =customerMapper.dtoToModel(customerDTO);
        customerRepository.save(customer);
        log.info("customerService:updateCustomer - customer updated");
        return customerMapper.modelToDto(customer);
    }
}