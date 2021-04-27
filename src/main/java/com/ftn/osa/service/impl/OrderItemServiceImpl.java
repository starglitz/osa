package com.ftn.osa.service.impl;

import com.ftn.osa.repository.OrderItemRepository;
import com.ftn.osa.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

}
