package com.ftn.osa.repository;

import com.ftn.osa.model.entity.Admin;
import com.ftn.osa.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long> {

//    Optional<User> findFirstByUsername(String username);

}
