package com.ftn.osa.service.impl;

import com.ftn.osa.repository.DiscountRepository;
import com.ftn.osa.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

}
