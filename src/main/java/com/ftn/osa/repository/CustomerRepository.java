package com.ftn.osa.repository;

import com.ftn.osa.model.entity.Customer;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT * FROM customer  WHERE user_id = (select user_id from user where username = ?1)",
            nativeQuery = true)
    Optional<Customer> findByUsername(String username);
}
