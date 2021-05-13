package com.ftn.osa.repository;

import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query(value = "SELECT * FROM seller  WHERE user_id = (select user_id from user where username = ?1)",
            nativeQuery = true)
    Optional<Seller> findByUsername(String username);

    @Query(value = "select * from seller where user_id in\n" +
            "(select user_id from article where article_id in\n" +
            "(select article_id from order_items where order_id in\n" +
            "(select order_id from orders where order_id =?1)))",
            nativeQuery = true)
    Optional<Seller> findByOrder(Long id);
}
