package com.ftn.osa.repository;

import com.ftn.osa.model.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount,Long> {

    @Query(value = "SELECT * FROM discount WHERE user_id = ?1",
            nativeQuery = true)
    List<Discount> findBySellerId(Long id);

}
