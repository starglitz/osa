package com.ftn.osa.rest.impl;

import com.ftn.osa.rest.dto.CustomerDTO;
import com.ftn.osa.rest.dto.OrderDTO;
import com.ftn.osa.rest.dto.OrderItemDTO;
import com.ftn.osa.rest.dto.OrderUpdateDTO;
import com.ftn.osa.model.entity.*;
import com.ftn.osa.rest.OrderApi;
import com.ftn.osa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class OrderApiImpl implements OrderApi {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private UserService userService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseEntity add(OrderDTO orderDto, Authentication authentication) throws URISyntaxException {
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        String username = userPrincipal.getUsername();
        User user = userService.findByUsername(username);
        Customer customer = customerService.findCustomerById(user.getId()).get();

        Order order = new Order();
        order.setDelivered(false);
        order.setTime(new Date());
        order.setCustomer(customer);

        Order orderJpa = orderService.save(order);

        List<OrderItem> itemsJpa = new ArrayList<>();

        for(OrderItemDTO item : orderDto.getItems()) {

            Article article = articleService.getArticleEntity(item.getArticle().getId());
            OrderItem itemFull = new OrderItem();
            itemFull.setArticle(article);
            itemFull.setAmount(item.getAmount());
            itemFull.setOrder(orderJpa);
            OrderItem itemJpa = orderItemService.save(itemFull);
            itemsJpa.add(itemJpa);
        }

        Order saved = orderService.save(order);
        return ResponseEntity
                .created(new URI("/orders/" + saved.getId()))
                .body(OrderDTO.fromEntity(saved));
    }

    @Override
    public ResponseEntity getOrdersByUser(Authentication authentication) {
        List<Order> orders = orderService.findByUser(authentication);
        return new ResponseEntity(OrderDTO.fromEntityList(orders),HttpStatus.OK);
    }

    @Override
    public ResponseEntity update(Long id, @Valid OrderUpdateDTO orderDTO) {
        Order order = new Order(id, orderDTO.isDelivered(), orderDTO.getRating(), orderDTO.getComment(),
                orderDTO.isAnonymous(), orderDTO.isArchived());
        order = orderService.update(order);
        return new ResponseEntity(orderDTO, HttpStatus.OK);
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
