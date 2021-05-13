package com.ftn.osa.repository;

import com.ftn.osa.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query(value = "SELECT * FROM orders where user_id = (select id from user where username = ?1)",
            nativeQuery = true)
    List<Order> findByUserUsername(String username);

    @Query(value = "select * from orders where order_id in (select order_id from order_items where article_id in(select article_id from article where user_id = ?1))",
            nativeQuery = true)
    List<Order> findBySellerId(Long id);
}
