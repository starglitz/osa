package com.ftn.osa.service;

import com.ftn.osa.model.entity.Discount;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DiscountService {

    Discount addDiscount(Discount discount);

    List<Discount> getByCurrentSeller(Authentication authentication);

    Discount findById(Long id);

    void delete(Long id);

    Discount update(Discount discount);
}
