package com.ftn.osa.rest.impl;

import com.ftn.osa.rest.SellerApi;
import com.ftn.osa.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

@Component
public class SellerApiImpl implements SellerApi {

    @Autowired
    private SellerService sellerService;

    @Override
    public ResponseEntity getAllSellers() {
            return new ResponseEntity(sellerService.getAllSellerListDTO(), HttpStatus.OK);
    }
}
