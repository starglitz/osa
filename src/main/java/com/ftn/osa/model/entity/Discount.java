package com.ftn.osa.model.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.dao.DataAccessException;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "discount")
public class Discount {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="discount_id", unique=true, nullable=false)
    private Long id;

    private int percent;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    private String description;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="user_id", nullable=false)
    private Seller seller;


    @ManyToMany
    @JoinTable(
            name = "discount_article",
            joinColumns = @JoinColumn(name = "discount_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id"))
    private Set<Article> articles = new HashSet<>();


}


