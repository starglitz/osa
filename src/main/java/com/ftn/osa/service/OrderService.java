package com.ftn.osa.service;

import com.ftn.osa.model.entity.Order;
import com.ftn.osa.rest.dto.OrderDTO;
import com.ftn.osa.rest.dto.OrderUpdateDTO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    OrderDTO save(OrderDTO orderDto, Authentication authentication);

    List<OrderDTO> findByUser(Authentication authentication);

    OrderDTO update(OrderUpdateDTO order);

    List<Order> findBySellerId(Long id);

    Order findById(Long id);

    List<Order> findByCurrentSeller(Authentication authentication);

    Order setDelivered(Order order);

    Order setArchived(Order order);
}
