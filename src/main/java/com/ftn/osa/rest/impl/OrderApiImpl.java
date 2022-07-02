package com.ftn.osa.rest.impl;

import com.ftn.osa.rest.dto.OrderDTO;
import com.ftn.osa.rest.dto.OrderUpdateDTO;
import com.ftn.osa.model.entity.*;
import com.ftn.osa.rest.OrderApi;
import com.ftn.osa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class OrderApiImpl implements OrderApi {

    @Autowired
    private OrderService orderService;

    @Override
    public ResponseEntity add(OrderDTO orderDto, Authentication authentication) throws URISyntaxException {
        OrderDTO saved = orderService.save(orderDto, authentication);
        return ResponseEntity
                .created(new URI("/orders/" + saved.getId()))
                .body(saved);
    }

    @Override
    public ResponseEntity getOrdersByUser(Authentication authentication) {
        return new ResponseEntity(orderService.findByUser(authentication), HttpStatus.OK);
    }

    @Override
    public ResponseEntity update(Long id, @Valid OrderUpdateDTO orderDTO) {
        return new ResponseEntity(orderService.update(orderDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getOrdersBySellerId(Long id) {
        List<Order> orders = orderService.findBySellerId(id);
        return new ResponseEntity(OrderDTO.fromEntityList(orders), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getOrdersByCurrentSeller(Authentication authentication) {
        List<Order> orders = orderService.findByCurrentSeller(authentication);
        return new ResponseEntity(OrderDTO.fromEntityList(orders), HttpStatus.OK);
    }

    @Override
    public ResponseEntity setDelivered(Long id, @Valid OrderDTO orderDTO) {
        Order order = orderService.findById(id);
        if(order == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        order = orderService.setDelivered(order);

        return new ResponseEntity(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<OrderDTO> setArchived(Long id, @Valid OrderDTO orderDTO) {
        Order order = orderService.findById(id);
        if(order == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        order = orderService.setArchived(order);
        return new ResponseEntity(HttpStatus.OK);
    }
}
