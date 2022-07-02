package com.ftn.osa.service;

import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.rest.dto.SellerDTO;
import com.ftn.osa.rest.dto.SellerListDTO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SellerService {

    List<Seller> getAll();

    List<SellerListDTO> getAllDto();

    Seller getLoggedIn(Authentication authentication);

    boolean update(SellerDTO seller);

    Seller getById(Long id);

    SellerListDTO getDTOById(Long id);

    SellerDTO createSeller(SellerDTO seller);

    double findAverageSellerRating(Long id);

}
