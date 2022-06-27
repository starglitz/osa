package com.ftn.osa.rest.impl;

import com.ftn.osa.rest.dto.CustomerDTO;
import com.ftn.osa.model.entity.*;
import com.ftn.osa.rest.CustomerApi;
import com.ftn.osa.service.CustomerService;
import com.ftn.osa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Component
public class CustomerApiImpl implements CustomerApi {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity getLoggedIn(Authentication authentication) {
        CustomerDTO dto = customerService.getLoggedIn(authentication);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Article> update(Long id, @Valid CustomerDTO customerDTO) {

        if(customerService.update(customerDTO, customerDTO.getPasswordValidate())) {
            return new ResponseEntity("updated", HttpStatus.OK);
        }
        else {
            return new ResponseEntity("bad data", HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<CustomerDTO> create(@RequestBody @Validated CustomerDTO customerDTO){

        CustomerDTO created = customerService.createCustomer(customerDTO);

        if(created == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
