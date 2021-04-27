package com.ftn.osa.repository;

import com.ftn.osa.model.entity.Customer;
import com.ftn.osa.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
//    Optional<User> findFirstByUsername(String username);
}
