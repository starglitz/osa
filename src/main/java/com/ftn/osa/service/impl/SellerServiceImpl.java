package com.ftn.osa.service.impl;

import com.ftn.osa.model.dto.SellerDTO;
import com.ftn.osa.model.dto.SellerListDTO;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.repository.SellerRepository;
import com.ftn.osa.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerRepository sellerRepository;


    @Override
    public List<SellerListDTO> getAllSellerListDTO() {
        List<Seller> sellers = sellerRepository.findAll();

        List<SellerListDTO> sellersForShow = new ArrayList<>();
        for(Seller seller : sellers) {
            SellerListDTO sellerForShow = new SellerListDTO(seller);
            sellersForShow.add(sellerForShow);
        }

        return sellersForShow;
    }

    @Override
    public SellerDTO getLoggedIn(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        System.out.println("TRENUTNI ULOGOVANI usernname =" + userPrincipal.getUsername());
        String username = userPrincipal.getUsername();
        Seller seller = sellerRepository.findFirstByUsername(username).get();
        SellerDTO sellerDTO = new SellerDTO(seller);
        return sellerDTO;
    }
}
