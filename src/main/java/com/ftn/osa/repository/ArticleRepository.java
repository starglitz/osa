package com.ftn.osa.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query(value = "SELECT * FROM article  WHERE user_id = (select id from user where username = ?1)",
            nativeQuery = true)
    List<Article> getArticlesByCurrentSellerUsername(String username);


    @Query(value = "SELECT * FROM article  WHERE user_id = ?1",
            nativeQuery = true)
    List<Article> getArticlesBySellerId(Long id);

}
