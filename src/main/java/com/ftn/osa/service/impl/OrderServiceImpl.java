package com.ftn.osa.service.impl;

import com.ftn.osa.model.dto.OrderDTO;
import com.ftn.osa.model.entity.Order;
import com.ftn.osa.repository.OrderRepository;
import com.ftn.osa.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<OrderDTO> findByUser(Authentication authentication) {
        System.out.println("before orders");
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        String username = userPrincipal.getUsername();

        List<Order> orders = orderRepository.findByUserUsername(username);
        System.out.println("after getting jpa orders");

        List<OrderDTO> orderDtos = new ArrayList<>();
        for(Order order : orders) {
            OrderDTO orderDTO = new OrderDTO(order);
            orderDtos.add(orderDTO);
        }

        return orderDtos;
    }

    @Override
    public OrderDTO update(OrderDTO orderDTO) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!1");
        System.out.println(orderDTO);
        Order order = orderRepository.findById(orderDTO.getId()).get();
        order.setDelivered(orderDTO.isDelivered());
        order.setComment(orderDTO.getComment());
        order.setRating(orderDTO.getRating());
        order.setAnonymous(orderDTO.isAnonymous());
        order.setArchived(orderDTO.isArchived());

        orderRepository.save(order);
        return orderDTO;
    }


}
