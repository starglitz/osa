package com.ftn.osa.rest.impl;

import com.ftn.osa.model.dto.OrderItemDTO;
import com.ftn.osa.model.entity.OrderItem;
import com.ftn.osa.rest.OrderItemApi;
import com.ftn.osa.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class OrderItemApiImpl implements OrderItemApi {

    @Autowired
    private OrderItemService orderItemService;

    @Override
    public ResponseEntity<OrderItem> add(OrderItemDTO orderItem, Authentication authentication) {
        System.out.println(orderItem);
        return new ResponseEntity("test", HttpStatus.OK);
    }
}
