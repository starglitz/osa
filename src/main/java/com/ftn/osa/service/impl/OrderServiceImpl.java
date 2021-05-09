package com.ftn.osa.service.impl;

import com.ftn.osa.model.entity.Order;
import com.ftn.osa.repository.OrderRepository;
import com.ftn.osa.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
