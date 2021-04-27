package com.ftn.osa.repository;

import com.ftn.osa.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findFirstByUsername(String username);

}
