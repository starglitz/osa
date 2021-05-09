package com.ftn.osa.service.impl;

import com.ftn.osa.model.entity.Customer;
import com.ftn.osa.repository.CustomerRepository;
import com.ftn.osa.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).get();
    }
}
