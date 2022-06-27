package com.ftn.osa.service;

import com.ftn.osa.model.entity.Customer;
import com.ftn.osa.rest.dto.CustomerDTO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CustomerService{

    CustomerDTO findById(Long id);

    Optional<Customer> findCustomerById(Long id);

    CustomerDTO getLoggedIn(Authentication authentication);

    boolean update(CustomerDTO customer, String passwordValidate);

    CustomerDTO createCustomer(CustomerDTO customer);
}
