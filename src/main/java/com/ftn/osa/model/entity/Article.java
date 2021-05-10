package com.ftn.osa.model.entity;

import com.ftn.osa.model.dto.ArticleDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String description;

    @NotNull
    @Positive
    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    @NotBlank
    private String path;

    @ManyToMany(mappedBy = "articles")
    private Set<Discount> discounts = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Seller seller;

    public Article(Long id, @NotBlank String name, @NotBlank String description,
                   @NotNull @Positive String price, @NotBlank String path,
                   Set<Discount> discounts, Seller seller) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.path = path;
        this.discounts = discounts;
        this.seller = seller;
    }
}
