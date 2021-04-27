package com.ftn.osa.rest.impl;

import com.ftn.osa.rest.OrderApi;
import com.ftn.osa.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderApiImpl implements OrderApi {

    @Autowired
    private OrderService orderService;

}
