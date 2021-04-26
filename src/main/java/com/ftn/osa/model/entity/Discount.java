package com.ftn.osa.model.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.dao.DataAccessException;

import javax.persistence.*;
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

    private Date from;

    private Date to;

    private String description;

    @ManyToOne
    @JoinColumn(name="seller_id", referencedColumnName="id", nullable=false)
    private Seller seller;

//    @ManyToMany(cascade = {
//            CascadeType.PERSIST,
//            CascadeType.MERGE
//    })

    @ManyToMany
    @JoinTable(
        name = "discount_article",
        joinColumns = @JoinColumn(name = "discount_id"),
        inverseJoinColumns = @JoinColumn(name = "article_id"))
    private Set<Article> articles = new HashSet<>();


//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public int getPercent() {
//        return percent;
//    }
//
//    public void setPercent(int percent) {
//        this.percent = percent;
//    }
//
//    public Date getFrom() {
//        return from;
//    }
//
//    public void setFrom(Date from) {
//        this.from = from;
//    }
//
//    public Date getTo() {
//        return to;
//    }
//
//    public void setTo(Date to) {
//        this.to = to;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public Seller getSeller() {
//        return seller;
//    }
//
//    public void setSeller(Seller seller) {
//        this.seller = seller;
//    }
//
//    public Set<Article> getArticles() {
//        return articles;
//    }
//
//    public void setArticles(Set<Article> articles) {
//        this.articles = articles;
//    }
//
//    public Discount() {
//    }
//
//    public Discount(int percent, Date from, Date to, String description, Seller seller, Set<Article> articles) {
//        this.percent = percent;
//        this.from = from;
//        this.to = to;
//        this.description = description;
//        this.seller = seller;
//        this.articles = articles;
//    }
}


