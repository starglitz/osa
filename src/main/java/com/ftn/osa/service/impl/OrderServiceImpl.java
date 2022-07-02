package com.ftn.osa.service.impl;

import com.ftn.osa.OsaApplication;
import com.ftn.osa.model.entity.*;
import com.ftn.osa.repository.OrderRepository;
import com.ftn.osa.repository.SellerRepository;
import com.ftn.osa.rest.dto.OrderDTO;
import com.ftn.osa.rest.dto.OrderItemDTO;
import com.ftn.osa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ArticleService articleService;


    @Override
    public OrderDTO save(OrderDTO orderDto, Authentication authentication) {

        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        String username = userPrincipal.getUsername();
        User user = userService.findByUsername(username);
        Customer customer = customerService.findCustomerById(user.getId()).get();

        Order order = new Order();
        order.setDelivered(false);
        order.setTime(new Date());
        order.setCustomer(customer);

        Order orderJpa = orderRepository.save(order);

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


        return new OrderDTO(orderRepository.save(order));
    }

    @Override
    public List<OrderDTO> findByUser(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        String username = userPrincipal.getUsername();

        List<Order> orders = orderRepository.findByUserUsername(username);

        return OrderDTO.fromEntityList(orders);
    }



    @Override
    public OrderDTO update(OrderDTO orderDTO) {

        Order update = new Order(orderDTO.getId(), orderDTO.isDelivered(), orderDTO.getRating(), orderDTO.getComment(),
                orderDTO.isAnonymous(), orderDTO.isArchived());

        Order order = orderRepository.findById(update.getId()).get();
        order.setDelivered(update.isDelivered());
        order.setComment(update.getComment());
        order.setRating(update.getRating());
        order.setAnonymous(update.isAnonymous());
        order.setArchived(update.isArchived());
        OsaApplication.log.info("Successfully updated an order with ID " + order.getId());
        orderRepository.save(order);
        return OrderDTO.fromEntity(order);
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
