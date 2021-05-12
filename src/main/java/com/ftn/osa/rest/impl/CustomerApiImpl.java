package com.ftn.osa.rest.impl;

import com.ftn.osa.model.dto.CustomerDTO;
import com.ftn.osa.model.entity.*;
import com.ftn.osa.rest.CustomerApi;
import com.ftn.osa.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
public class CustomerApiImpl implements CustomerApi {

    @Autowired
    private CustomerService customerService;

    @Override
    public ResponseEntity getLoggedIn(Authentication authentication) {
        return new ResponseEntity(customerService.getLoggedIn(authentication), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Article> update(Long id, @Valid CustomerDTO customerDTO) {
        User user = new User(customerDTO.getId(), customerDTO.getName(), customerDTO.getSurname(),
                customerDTO.getUsername(), customerDTO.getPassword(), true, Role.CUSTOMER);

        Customer customer = new Customer(customerDTO.getAddress(), user, user.getId());

        if(customerService.update(customer, customerDTO.getPasswordValidate())) {
            return new ResponseEntity("updated", HttpStatus.OK);
        }
        else {
            return new ResponseEntity("bad data", HttpStatus.BAD_REQUEST);
        }

    }
}
