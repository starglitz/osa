package com.ftn.osa.rest.impl;

import com.ftn.osa.rest.dto.SellerDTO;
import com.ftn.osa.rest.dto.SellerListDTO;
import com.ftn.osa.model.entity.Article;
import com.ftn.osa.service.SellerService;
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
@RequestMapping("/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN', 'CUSTOMER')")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getAllSellers() {
        return new ResponseEntity(sellerService.getAllDto(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN', 'CUSTOMER')")
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getSeller(@PathVariable("id") Long id) {
        SellerListDTO seller = sellerService.getDTOById(id);
        if(seller != null) {
            return new ResponseEntity(seller, HttpStatus.OK);
        }
        return new ResponseEntity("Seller not found", HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping(value = "/profile",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getLoggedIn(Authentication authentication) {
        SellerDTO sellerDTO = new SellerDTO(sellerService.getLoggedIn(authentication));
        return new ResponseEntity(sellerDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PutMapping(value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Article> update(@PathVariable("id") Long id, @Valid @RequestBody SellerDTO sellerDTO) {

        if(sellerService.update(sellerDTO)) {
            return new ResponseEntity("updated", HttpStatus.OK);
        }
        else {
            return new ResponseEntity("bad data", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping
    public ResponseEntity<SellerDTO> create(@RequestBody @Valid SellerDTO sellerDTO) throws URISyntaxException {

        SellerDTO createdSeller = sellerService.createSeller(sellerDTO);

        if(createdSeller == null){
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        return ResponseEntity
                .created(new URI("/sellers/" + createdSeller.getId()))
                .body(createdSeller);
    }
}
