package com.ftn.osa.model.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.dao.DataAccessException;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotNull
    private int percent;

    @NotNull
    private LocalDate dateFrom;

    @NotNull
    private LocalDate dateTo;

    @NotBlank
    private String description;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="user_id", nullable=false)
    private Seller seller;


    @ManyToMany
    @JoinTable(
            name = "discount_article",
            joinColumns = @JoinColumn(name = "discount_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id"))
    private List<Article> articles = new ArrayList<>();

    public Discount(@NotNull int percent,@NotNull LocalDate dateFrom,
                    @NotNull LocalDate dateTo, @NotBlank String description,
                    Seller seller, List<Article> articles) {
        this.percent = percent;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.description = description;
        this.seller = seller;
        this.articles = articles;
    }

    public Discount(Long id, @NotNull int percent, @NotNull LocalDate dateFrom,
                    @NotNull LocalDate dateTo, @NotBlank String description,
                    List<Article> articles) {
        this.id = id;
        this.percent = percent;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.description = description;
        this.articles = articles;
    }
}


