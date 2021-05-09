package com.ftn.osa.rest;

import com.ftn.osa.model.dto.OrderDTO;
import com.ftn.osa.model.dto.OrderItemDTO;
import com.ftn.osa.model.entity.Order;
import com.ftn.osa.model.entity.OrderItem;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/orderItem")
public interface OrderItemApi {
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<OrderItem> add(@RequestBody OrderItemDTO orderItem, Authentication authentication);

}
