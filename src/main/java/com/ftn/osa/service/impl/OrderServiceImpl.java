package com.ftn.osa.service.impl;

import com.ftn.osa.OsaApplication;
import com.ftn.osa.model.dto.ArticleDTO;
import com.ftn.osa.model.dto.OrderDTO;
import com.ftn.osa.model.dto.OrderUpdateDTO;
import com.ftn.osa.model.dto.SimpleQueryEs;
import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.Order;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.model.es.ArticleES;
import com.ftn.osa.model.es.OrderES;
import com.ftn.osa.repository.OrderRepository;
import com.ftn.osa.repository.SellerRepository;
import com.ftn.osa.searchRepository.OrderSearchRepository;
import com.ftn.osa.service.OrderService;
import org.apache.log4j.Logger;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    public static Logger log = Logger.getLogger(OrderServiceImpl.class.getName());

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderSearchRepository orderSearchRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


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
//        System.out.println("after getting jpa orders");
//
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
    public List<OrderES> getAll(String query) {
        List<OrderES> l = new ArrayList<>();
        if(!query.equals("")) {
            return orderSearchRepository.findAllByComment(query);
        }
        Iterable<OrderES> orders = orderSearchRepository.findAll();
        orders.forEach(l::add);
        System.out.println(orders);

        return l;
    }

    @Override
    public List<OrderES> findByRatingRange(int start, int end) {
        String range = start + "-" + end;
        QueryBuilder ratingQuery = SearchQueryGenerator.createRangeQueryBuilder(new SimpleQueryEs("rating", range));

        BoolQueryBuilder boolQueryRating = QueryBuilders
                .boolQuery()
                .must(ratingQuery);

        SearchHits<OrderES> orders = searchByBoolQuery(boolQueryRating);
        return orders.map(SearchHit::getContent).toList();
    }

    private SearchHits<OrderES> searchByBoolQuery(BoolQueryBuilder boolQueryBuilder) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();

        return elasticsearchRestTemplate.search(searchQuery, OrderES.class,  IndexCoordinates.of("order"));
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
