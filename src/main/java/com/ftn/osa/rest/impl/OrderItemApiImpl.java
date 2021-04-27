package com.ftn.osa.rest.impl;

import com.ftn.osa.rest.OrderItemApi;
import com.ftn.osa.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderItemApiImpl implements OrderItemApi {

    @Autowired
    private OrderItemService orderItemService;

}
