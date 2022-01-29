package com.ftn.osa.service;

import com.ftn.osa.model.dto.OrderDTO;
import com.ftn.osa.model.dto.OrderUpdateDTO;
import com.ftn.osa.model.entity.Order;
import com.ftn.osa.model.es.ArticleES;
import com.ftn.osa.model.es.OrderES;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    Order save(Order order);

    List<Order> findByUser(Authentication authentication);

    Order update(Order order);

    List<Order> findBySellerId(Long id);

    Order findById(Long id);

    List<OrderES> getAll(String query);

    public List<OrderES> findByRatingRange(int start, int end);

    List<Order> findByCurrentSeller(Authentication authentication);

    Order setDelivered(Order order);

    Order setArchived(Order order);
}
