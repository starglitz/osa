package com.ftn.osa.rest.impl;

import com.ftn.osa.rest.dto.OrderDTO;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        List<Seller> sellers = sellerService.getAll();

        List<SellerListDTO> sellersForShow = new ArrayList<>();
        for(Seller seller : sellers) {
            SellerListDTO sellerForShow = new SellerListDTO(seller);

            double rating = sellerService.findAverageSellerRating(seller.getId());
            sellerForShow.setRating(rating);
            sellersForShow.add(sellerForShow);
        }

        return new ResponseEntity(sellersForShow, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getSeller(Long id) {
        Seller seller = sellerService.getById(id);

        if(seller != null) {

            double rating = sellerService.findAverageSellerRating(id);
            SellerListDTO dto = new SellerListDTO(seller);
            dto.setRating(rating);
            return new ResponseEntity(dto, HttpStatus.OK);
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

        User user = new User(sellerDTO.getId(), sellerDTO.getName(), sellerDTO.getSurname(),
                sellerDTO.getUsername(), sellerDTO.getPassword(), true, Role.SELLER);

        Seller seller = new Seller(sellerDTO.getSince(), sellerDTO.getEmail(),
                sellerDTO.getAddress(), sellerDTO.getSellerName(), user, sellerDTO.getId());

        if(sellerService.update(seller, sellerDTO.getPasswordValidate())) {
            return new ResponseEntity("updated", HttpStatus.OK);
        }
        else {
            return new ResponseEntity("bad data", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity<SellerDTO> create(@RequestBody @Validated SellerDTO sellerDTO) throws URISyntaxException {

        User newUser = new User(sellerDTO.getName(), sellerDTO.getSurname(),
                sellerDTO.getUsername(), passwordEncoder.encode(sellerDTO.getPassword()),
                true, Role.SELLER);

        Seller newSeller = new Seller(new Date(), sellerDTO.getEmail(), sellerDTO.getAddress(),
                sellerDTO.getSellerName(), newUser);

        Seller createdSeller = sellerService.createSeller(newSeller);

        if(createdSeller == null){
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        SellerDTO dto = new SellerDTO(createdSeller);
        return ResponseEntity
                .created(new URI("/sellers/" + dto.getId()))
                .body(dto);
    }
}
