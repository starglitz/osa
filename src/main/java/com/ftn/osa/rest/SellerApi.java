package com.ftn.osa.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/sellers")
public interface SellerApi {

    @GetMapping(value = "/all",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getAllSellers();

}
