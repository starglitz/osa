package com.ftn.osa.service;

import com.ftn.osa.model.dto.SellerListDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SellerService {

    List<SellerListDTO> getAllSellerListDTO();

}
