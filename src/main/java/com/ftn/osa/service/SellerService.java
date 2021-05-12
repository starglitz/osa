package com.ftn.osa.service;

import com.ftn.osa.model.dto.SellerDTO;
import com.ftn.osa.model.dto.SellerListDTO;
import com.ftn.osa.model.entity.Seller;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SellerService {

    List<SellerListDTO> getAllSellerListDTO();

    SellerDTO getLoggedIn(Authentication authentication);

    boolean update(Seller seller, String validatePassword);

    Seller getById(Long id);

}
