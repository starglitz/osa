package com.ftn.osa.rest;

import com.ftn.osa.model.dto.DiscountDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/discounts")
public interface DiscountApi {

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity add(@Valid @RequestBody DiscountDTO discountDTO, Authentication authentication);

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping(value = "/seller/me",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getByCurrentSeller(Authentication authentication);

    @PreAuthorize("hasRole('SELLER')")
    @DeleteMapping(value = "/{id}")
    ResponseEntity delete(@PathVariable("id") Long id);

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity get(@PathVariable("id") Long id);

    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    @PutMapping(value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity update(@PathVariable("id") Long id,@Valid @RequestBody DiscountDTO discountDTO);


}
