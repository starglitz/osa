package com.ftn.osa.rest;

import com.ftn.osa.model.dto.ArticleDTO;
import com.ftn.osa.model.dto.SellerDTO;
import com.ftn.osa.model.entity.Article;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/sellers")
public interface SellerApi {

    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN', 'CUSTOMER')")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getAllSellers();

    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN', 'CUSTOMER')")
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getSeller(@PathVariable("id") Long id);

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping(value = "/profile",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getLoggedIn(Authentication authentication);


    @PreAuthorize("hasRole('SELLER')")
    @PutMapping(value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<Article> update(@PathVariable("id") Long id, @Valid @RequestBody SellerDTO sellerDTO);
}
