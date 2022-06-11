package com.ftn.osa.service.impl;

import com.ftn.osa.OsaApplication;
import com.ftn.osa.model.dto.ArticleDTO;
import com.ftn.osa.model.dto.OrderDTO;
import com.ftn.osa.model.dto.OrderUpdateDTO;
import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.Order;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.repository.OrderRepository;
import com.ftn.osa.repository.SellerRepository;
import com.ftn.osa.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SellerRepository sellerRepository;


    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findByUser(Authentication authentication) {
        System.out.println("before orders");
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        System.out.println(userPrincipal.getUsername());
        String username = userPrincipal.getUsername();

        List<Order> orders = orderRepository.findByUserUsername(username);

//        List<OrderDTO> orderDtos = new ArrayList<>();
//        for(Order order : orders) {
//            OrderDTO orderDTO = new OrderDTO(order);
//            orderDtos.add(orderDTO);
//        }

        return orders;
    }



    @Override
    public Order update(Order update) {

        Order order = orderRepository.findById(update.getId()).get();
        order.setDelivered(update.isDelivered());
        order.setComment(update.getComment());
        order.setRating(update.getRating());
        order.setAnonymous(update.isAnonymous());
        order.setArchived(update.isArchived());
        OsaApplication.log.info("Successfully updated an order with ID " + order.getId());
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order setDelivered(Order order) {

        order.setDelivered(true);
        order = orderRepository.save(order);
        OsaApplication.log.info("Order with ID " + order.getId() + " set to delivered");
        return order;
    }

    @Override
    public Order setArchived(Order order) {

        order.setArchived(true);
        order = orderRepository.save(order);
        OsaApplication.log.info("Order with ID " + order.getId() + " set to archived");
        return order;
    }

    @Override
    public List<Order> findBySellerId(Long id) {
        List<Order> orders = orderRepository.findBySellerId(id);

//        List<OrderDTO> orderDTOS = new ArrayList<>();
//        for(Order order : orders) {
//            OrderDTO orderDTO = new OrderDTO(order);
//            orderDTOS.add(orderDTO);
//        }

        return orders;
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public List<Order> findByCurrentSeller(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        String username = userPrincipal.getUsername();
        System.out.println("USER USERNAME: " + username);

        Seller seller = sellerRepository.findByUsername(username).get();
        System.out.println("SELLER: " + seller);
        List<Order> orders = orderRepository.findBySellerId(seller.getId());
//        List<OrderDTO> orderDTOS = new ArrayList<>();
//        for(Order order : orders) {
//            OrderDTO orderDTO = new OrderDTO(order);
//            orderDTOS.add(orderDTO);
//        }

        return orders;
    }

}
