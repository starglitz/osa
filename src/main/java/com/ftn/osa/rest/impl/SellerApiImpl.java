package com.ftn.osa.rest.impl;

import com.ftn.osa.model.dto.SellerDTO;
import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.Role;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.model.entity.User;
import com.ftn.osa.rest.SellerApi;
import com.ftn.osa.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.validation.Valid;

@Component
public class SellerApiImpl implements SellerApi {

    @Autowired
    private SellerService sellerService;

    @Override
    public ResponseEntity getAllSellers() {
            return new ResponseEntity(sellerService.getAllSellerListDTO(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getLoggedIn(Authentication authentication) {
        return new ResponseEntity(sellerService.getLoggedIn(authentication), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Article> update(Long id, @Valid SellerDTO sellerDTO) {

        User user = new User(sellerDTO.getId(), sellerDTO.getName(), sellerDTO.getSurname(),
                sellerDTO.getUsername(), sellerDTO.getPassword(), true, Role.SELLER);

        Seller seller = new Seller(sellerDTO.getSince(), sellerDTO.getEmail(),
                sellerDTO.getAddress(), sellerDTO.getSellerName(), user, sellerDTO.getId());


        return new ResponseEntity(sellerService.update(seller), HttpStatus.OK);
    }
}
