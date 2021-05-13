package com.ftn.osa.service.impl;

import com.ftn.osa.model.dto.OrderDTO;
import com.ftn.osa.model.dto.OrderUpdateDTO;
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


    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<OrderDTO> findByUser(Authentication authentication) {
        System.out.println("before orders");
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        System.out.println(userPrincipal.getUsername());
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
    public OrderUpdateDTO update(OrderUpdateDTO orderDTO) {

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!1");
        System.out.println(orderDTO);
        Order order = orderRepository.findById(orderDTO.getId()).get();
        order.setDelivered(orderDTO.isDelivered());
        order.setComment(orderDTO.getComment());
        order.setRating(orderDTO.getRating());
        order.setAnonymous(orderDTO.isAnonymous());
        order.setArchived(orderDTO.isArchived());

//        Seller seller = sellerRepository.findByOrder(order.getId()).orElse(null);
//        if(seller == null){
//            ok = false;
//        }
//        else {
//            List<Integer> previousRatings = findPreviousRatings(seller.getId());
//            previousRatings.add(orderDTO.getRating());
//
//            double average = calculateAverage(previousRatings);
//
//        }

        orderRepository.save(order);
        return orderDTO;
    }

    @Override
    public boolean setDelivered(OrderDTO orderDTO) {
        boolean ok = true;
        Order order = orderRepository.findById(orderDTO.getId()).orElse(null);
        if(order == null) {
            ok = false;
        }
        else {
            order.setDelivered(true);
            orderRepository.save(order);
        }
        return ok;
    }

    @Override
    public List<OrderDTO> findBySellerId(Long id) {
        List<Order> orders = orderRepository.findBySellerId(id);

        List<OrderDTO> orderDTOS = new ArrayList<>();
        for(Order order : orders) {
            OrderDTO orderDTO = new OrderDTO(order);
            orderDTOS.add(orderDTO);
        }

        return orderDTOS;
    }

//    @Override
//    public double findAverageSellerRating(Long sellerId) {
//        List<OrderDTO> sellersOrders = findBySellerId(sellerId);
//
//        List<Integer> ratings = new ArrayList<>();
//        for(OrderDTO order : sellersOrders) {
//            ratings.add(order.getRating());
//        }
//
//         return calculateAverage(ratings);
//    }
//
//    @Override
//    public List<Integer> findPreviousRatings(Long sellerId) {
//        List<OrderDTO> sellersOrders = findBySellerId(sellerId);
//
//        List<Integer> ratings = new ArrayList<>();
//        for(OrderDTO order : sellersOrders) {
//            ratings.add(order.getRating());
//        }
//
//        return ratings;
//    }
//
//    private double calculateAverage(List <Integer> ratings) {
//        return ratings.stream()
//                .mapToDouble(d -> d)
//                .average()
//                .orElse(0.0);
//    }





}
