package com.ftn.osa.rest.impl;

import com.ftn.osa.model.dto.OrderDTO;
import com.ftn.osa.model.dto.OrderItemDTO;
import com.ftn.osa.model.dto.OrderUpdateDTO;
import com.ftn.osa.model.entity.*;
import com.ftn.osa.rest.OrderApi;
import com.ftn.osa.service.*;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
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
    public ResponseEntity<Order> add(OrderDTO orderDto, Authentication authentication) {
        System.out.println(orderDto);

        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        String username = userPrincipal.getUsername();
        User user = userService.findByUsername(username);
        Customer customer = customerService.findById(user.getId());

        Order order = new Order();
        order.setDelivered(false);
        order.setTime(new Date());
        order.setCustomer(customer);

        Order orderJpa = orderService.save(order);

        List<OrderItem> itemsJpa = new ArrayList<>();

        for(OrderItemDTO item : orderDto.getItems()) {

            Article article = articleService.getArticle(item.getArticle().getId());
            OrderItem itemFull = new OrderItem();
            itemFull.setArticle(article);
            itemFull.setAmount(item.getAmount());
            itemFull.setOrder(orderJpa);
            //entityManager.merge(item);
            OrderItem itemJpa = orderItemService.save(itemFull);
            //orderItemService.save(item);
            itemsJpa.add(itemJpa);
            System.out.println(itemJpa);
        }

        orderService.save(order);
        System.out.println(order);

        return new ResponseEntity("Successfully ordered", HttpStatus.OK);
    }

    @Override
    public ResponseEntity getOrdersByUser(Authentication authentication) {
        return new ResponseEntity(orderService.findByUser(authentication),HttpStatus.OK);
    }

    @Override
    public ResponseEntity update(Long id, @Valid OrderUpdateDTO orderDTO) {
        return new ResponseEntity(orderService.update(orderDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getOrdersBySellerId(Long id) {
        System.out.println("DOES THIS WOOOOORRRKKKKKKK!?!?!?!?!");
        double rating = sellerService.findAverageSellerRating(id);
        System.out.println("SELLER RATING: " + rating);
        return new ResponseEntity(orderService.findBySellerId(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getOrdersByCurrentSeller(Authentication authentication) {
        return new ResponseEntity(orderService.findByCurrentSeller(authentication), HttpStatus.OK);
    }

    @Override
    public ResponseEntity setDelivered(Long id, @Valid OrderDTO orderDTO) {
        boolean ok = orderService.setDelivered(orderDTO);
        if(ok) {
            return new ResponseEntity("Successfuly set to delivered", HttpStatus.OK);
        }
        return new ResponseEntity("Bad data sent", HttpStatus.BAD_REQUEST);

    }
}
