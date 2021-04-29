package com.ftn.osa.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="article_id", unique=true, nullable=false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    private String path;

    @ManyToMany(mappedBy = "articles")
    private Set<Discount> discounts = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Seller seller;
}
