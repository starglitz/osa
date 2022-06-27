package com.ftn.osa.rest;

import com.ftn.osa.rest.dto.CustomerDTO;
import com.ftn.osa.model.entity.Article;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/customers")
public interface CustomerApi {

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping(value = "/profile",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getLoggedIn(Authentication authentication);

    @PostMapping
    public ResponseEntity<CustomerDTO> create(@RequestBody @Valid CustomerDTO newUser);

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping(value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<Article> update(@PathVariable("id") Long id, @Valid @RequestBody CustomerDTO customerDTO);
}