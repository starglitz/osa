package com.ftn.osa.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/customers")
public interface CustomerApi {

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping(value = "/profile",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getLoggedIn(Authentication authentication);
}