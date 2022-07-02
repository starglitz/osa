package com.ftn.osa.rest.impl;

import com.ftn.osa.rest.dto.SellerDTO;
import com.ftn.osa.rest.dto.SellerListDTO;
import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.Role;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.model.entity.User;
import com.ftn.osa.rest.SellerApi;
import com.ftn.osa.service.SellerService;
import com.ftn.osa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class SellerApiImpl implements SellerApi {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity getAllSellers() {
        return new ResponseEntity(sellerService.getAllDto(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getSeller(Long id) {
        SellerListDTO seller = sellerService.getDTOById(id);
        if(seller != null) {
            return new ResponseEntity(seller, HttpStatus.OK);
        }
        return new ResponseEntity("Seller not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity getLoggedIn(Authentication authentication) {
        SellerDTO sellerDTO = new SellerDTO(sellerService.getLoggedIn(authentication));
        return new ResponseEntity(sellerDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Article> update(Long id, @Valid SellerDTO sellerDTO) {

        if(sellerService.update(sellerDTO)) {
            return new ResponseEntity("updated", HttpStatus.OK);
        }
        else {
            return new ResponseEntity("bad data", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity<SellerDTO> create(@RequestBody @Validated SellerDTO sellerDTO) throws URISyntaxException {

        SellerDTO createdSeller = sellerService.createSeller(sellerDTO);

        if(createdSeller == null){
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        return ResponseEntity
                .created(new URI("/sellers/" + createdSeller.getId()))
                .body(createdSeller);
    }
}
