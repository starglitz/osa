package com.ftn.osa.rest.impl;

import com.ftn.osa.model.dto.OrderDTO;
import com.ftn.osa.model.entity.*;
import com.ftn.osa.rest.OrderApi;
import com.ftn.osa.service.CustomerService;
import com.ftn.osa.service.OrderItemService;
import com.ftn.osa.service.OrderService;
import com.ftn.osa.service.UserService;
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

    @PersistenceContext()
    private EntityManager entityManager;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Override
    public ResponseEntity<Order> add(OrderDTO orderDto, Authentication authentication) {
        System.out.println(orderDto);


        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        System.out.println("TRENUTNI ULOGOVANI usernname =" + userPrincipal.getUsername());
        String username = userPrincipal.getUsername();
        User user = userService.findByUsername(username);
        Customer customer = customerService.findById(user.getId());


        Order order = new Order();
        order.setDelivered(false);
        order.setTime(new Date());
        order.setCustomer(customer);
        //order.setItems(orderDto.getItems());

        Order orderJpa = orderService.save(order);


        List<OrderItem> itemsJpa = new ArrayList<>();

        for(OrderItem item : orderDto.getItems()) {
            item.setOrder(orderJpa);
            //entityManager.merge(item);
            OrderItem itemJpa = orderItemService.save(item);
            //orderItemService.save(item);
            itemsJpa.add(itemJpa);
            System.out.println(item);
        }

        //order.setItems(itemsJpa);
        //orderService.save(order)
        orderService.save(order);


//        for(OrderItem item : itemsJpa) {
//            item.setOrder(orderJpa);
//            orderItemService.save(item);
//        }
        //entityManager.merge(order);

        System.out.println(order);


        return new ResponseEntity("test", HttpStatus.OK);
    }
}
