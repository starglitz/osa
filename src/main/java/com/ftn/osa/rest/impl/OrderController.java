package com.ftn.osa.rest.impl;

import com.ftn.osa.rest.dto.OrderDTO;
import com.ftn.osa.rest.dto.OrderUpdateDTO;
import com.ftn.osa.model.entity.*;
import com.ftn.osa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity add(@RequestBody OrderDTO orderDto, Authentication authentication) throws URISyntaxException {
        OrderDTO saved = orderService.save(orderDto, authentication);
        return ResponseEntity
                .created(new URI("/orders/" + saved.getId()))
                .body(saved);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping(value = "/customer/me",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getOrdersByUser(Authentication authentication) {
        return new ResponseEntity(orderService.findByUser(authentication), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    @PutMapping(value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody OrderUpdateDTO orderDTO) {
        return new ResponseEntity(orderService.update(orderDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN','SELLER')")
    @GetMapping(value = "/seller/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getOrdersBySellerId(@PathVariable("id") Long id) {
        List<Order> orders = orderService.findBySellerId(id);
        return new ResponseEntity(OrderDTO.fromEntityList(orders), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping(value = "/seller/me",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getOrdersByCurrentSeller(Authentication authentication) {
        List<Order> orders = orderService.findByCurrentSeller(authentication);
        return new ResponseEntity(OrderDTO.fromEntityList(orders), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    @PutMapping(value = "/delivered/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity setDelivered(@PathVariable("id")Long id, @Valid @RequestBody OrderDTO orderDTO) {
        Order order = orderService.findById(id);
        if(order == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        order = orderService.setDelivered(order);

        return new ResponseEntity(HttpStatus.OK);

    }

    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    @PutMapping(value = "/archived/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<OrderDTO> setArchived(@PathVariable("id") Long id, @Valid @RequestBody OrderDTO orderDTO) {
        Order order = orderService.findById(id);
        if(order == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        order = orderService.setArchived(order);
        return new ResponseEntity(HttpStatus.OK);
    }
}
