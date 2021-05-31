package com.ftn.osa.service;

import com.ftn.osa.model.dto.CustomerDTO;
import com.ftn.osa.model.dto.SellerDTO;
import com.ftn.osa.model.entity.Customer;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService{

    Customer findById(Long id);

    Customer getLoggedIn(Authentication authentication);

    boolean update(Customer customer, String passwordValidate);

    Customer createCustomer(Customer customer);
}
