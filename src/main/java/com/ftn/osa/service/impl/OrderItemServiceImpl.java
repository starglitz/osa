package com.ftn.osa.service.impl;

import com.ftn.osa.model.entity.OrderItem;
import com.ftn.osa.repository.OrderItemRepository;
import com.ftn.osa.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem save(OrderItem item) {
        return orderItemRepository.save(item);
    }

    @Override
    public OrderItem find(Long id) {
        return orderItemRepository.findById(id).get();
    }
}
