package com.ftn.osa.rest.impl;

import com.ftn.osa.rest.CustomerApi;
import com.ftn.osa.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CustomerApiImpl implements CustomerApi {

    @Autowired
    private CustomerService customerService;

    @Override
    public ResponseEntity getLoggedIn(Authentication authentication) {
        return new ResponseEntity(customerService.getLoggedIn(authentication), HttpStatus.OK);
    }
}
