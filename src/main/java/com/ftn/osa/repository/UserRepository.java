package com.ftn.osa.repository;

import com.ftn.osa.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findFirstByUsername(String username);

    @Query(value = "SELECT * FROM user  WHERE role not like 'ADMIN'",
            nativeQuery = true)
    public List<User> findAllNoAdmin();

}
