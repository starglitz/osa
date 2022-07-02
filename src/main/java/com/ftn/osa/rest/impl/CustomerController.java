package com.ftn.osa.rest.impl;

import com.ftn.osa.rest.dto.CustomerDTO;
import com.ftn.osa.model.entity.*;
import com.ftn.osa.service.CustomerService;
import com.ftn.osa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@CrossOrigin
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping(value = "/profile",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getLoggedIn(Authentication authentication) {
        CustomerDTO dto = customerService.getLoggedIn(authentication);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping(value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Article> update(@PathVariable("id") Long id, @Valid @RequestBody CustomerDTO customerDTO) {

        if(customerService.update(customerDTO, customerDTO.getPasswordValidate())) {
            return new ResponseEntity("updated", HttpStatus.OK);
        }
        else {
            return new ResponseEntity("bad data", HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping
    public ResponseEntity<CustomerDTO> create(@RequestBody @Valid CustomerDTO customerDTO) throws URISyntaxException {

        CustomerDTO created = customerService.createCustomer(customerDTO);

        if(created == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity
                .created(new URI("/customers/" + created.getId()))
                .body(created);
    }
}
