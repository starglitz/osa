package com.ftn.osa.service;

import com.ftn.osa.model.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService{

    Customer findById(Long id);
}
