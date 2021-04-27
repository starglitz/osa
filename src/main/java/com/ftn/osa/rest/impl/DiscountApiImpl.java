package com.ftn.osa.rest.impl;

import com.ftn.osa.rest.DiscountApi;
import com.ftn.osa.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class DiscountApiImpl implements DiscountApi {

    @Autowired
    private DiscountService discountService;

}
