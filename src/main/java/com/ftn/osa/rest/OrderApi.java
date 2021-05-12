package com.ftn.osa.rest;

import com.ftn.osa.model.dto.ArticleDTO;
import com.ftn.osa.model.dto.OrderDTO;
import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/order")
public interface OrderApi {

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<Order> add(@RequestBody OrderDTO order, Authentication authentication);

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping(value = "/customer/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getOrdersByUser(Authentication authentication);

    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    @PutMapping(value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<OrderDTO> update(@PathVariable("id") Long id,@Valid @RequestBody OrderDTO orderDTO);
}
