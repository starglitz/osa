package com.ftn.osa.service;

import com.ftn.osa.model.entity.Discount;
import com.ftn.osa.rest.dto.DiscountDTO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DiscountService {

    DiscountDTO addDiscount(DiscountDTO discount, Authentication authentication);

    List<Discount> getByCurrentSeller(Authentication authentication);

    Discount findEntityById(Long id);

    DiscountDTO findById(Long id);

    void delete(Long id);

    DiscountDTO update(DiscountDTO discount);
}
