package com.ftn.osa.service;

import com.ftn.osa.model.dto.OrderDTO;
import com.ftn.osa.model.entity.Order;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    Order save(Order order);

    List<OrderDTO> findByUser(Authentication authentication);

    OrderDTO update(OrderDTO orderDTO);
}
