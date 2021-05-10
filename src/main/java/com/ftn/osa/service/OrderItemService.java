package com.ftn.osa.service;

import com.ftn.osa.model.entity.OrderItem;
import org.springframework.stereotype.Service;

@Service
public interface OrderItemService {

    OrderItem save(OrderItem item);

    OrderItem find(Long id);

}
