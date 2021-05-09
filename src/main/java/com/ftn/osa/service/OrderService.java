package com.ftn.osa.service;

import com.ftn.osa.model.entity.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    Order save(Order order);


}
