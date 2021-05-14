package com.ftn.osa.service;

import com.ftn.osa.model.dto.DiscountDTO;
import com.ftn.osa.model.entity.Discount;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface DiscountService {

    boolean addDiscount(DiscountDTO discountDTO, Authentication authentication);
}
