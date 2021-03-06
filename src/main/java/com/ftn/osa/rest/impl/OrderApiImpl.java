package com.ftn.osa.rest.impl;

import com.ftn.osa.model.dto.OrderDTO;
import com.ftn.osa.model.dto.OrderItemDTO;
import com.ftn.osa.model.dto.OrderUpdateDTO;
import com.ftn.osa.model.entity.*;
import com.ftn.osa.model.es.OrderES;
import com.ftn.osa.rest.OrderApi;
import com.ftn.osa.service.*;
import org.apache.coyote.Response;
import org.aspectj.weaver.ast.Or;
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
    public ResponseEntity getAll(String query) {
        return new ResponseEntity<>(orderService.getAll(query), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getByRatingRange(int start, int end) {
        return new ResponseEntity(orderService.findByRatingRange(start, end), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Order> add(OrderDTO orderDto, Authentication authentication) {
        System.out.println(orderDto);
        System.out.println("Im in add order");


        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        String username = userPrincipal.getUsername();
        User user = userService.findByUsername(username);
        Customer customer = customerService.findById(user.getId());
        System.out.println(customer);


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
          //  System.out.println(itemJpa);
        }

        orderService.save(order);
      //  System.out.println(order);

        return new ResponseEntity("Successfully ordered", HttpStatus.OK);
    }

    @Override
    public ResponseEntity getOrdersByUser(Authentication authentication) {
        List<Order> orders = orderService.findByUser(authentication);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for(Order order : orders) {
            OrderDTO orderDTO = new OrderDTO(order);
            orderDTOS.add(orderDTO);
        }
        return new ResponseEntity(orderDTOS,HttpStatus.OK);
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
        double rating = sellerService.findAverageSellerRating(id);

        List<Order> orders = orderService.findBySellerId(id);

        List<OrderDTO> orderDTOS = new ArrayList<>();
        for(Order order : orders) {
            OrderDTO orderDTO = new OrderDTO(order);
            orderDTOS.add(orderDTO);
        }

        return new ResponseEntity(orderDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getOrdersByCurrentSeller(Authentication authentication) {

        List<Order> orders = orderService.findByCurrentSeller(authentication);

        List<OrderDTO> orderDTOS = new ArrayList<>();
        for(Order order : orders) {
            OrderDTO orderDTO = new OrderDTO(order);
            orderDTOS.add(orderDTO);
        }

        return new ResponseEntity(orderDTOS, HttpStatus.OK);
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
