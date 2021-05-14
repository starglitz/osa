package com.ftn.osa.rest.impl;

import com.ftn.osa.model.dto.DiscountDTO;
import com.ftn.osa.rest.DiscountApi;
import com.ftn.osa.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
public class DiscountApiImpl implements DiscountApi {

    @Autowired
    private DiscountService discountService;

    @Override
    public ResponseEntity add(@Valid DiscountDTO discountDTO, Authentication authentication) {
        boolean ok = discountService.addDiscount(discountDTO, authentication);
        if(!ok) {
            return new ResponseEntity("Bad data sent", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(discountDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getByCurrentSeller(Authentication authentication) {
        return new ResponseEntity(discountService.getByCurrentSeller(authentication), HttpStatus.OK);
    }
}
