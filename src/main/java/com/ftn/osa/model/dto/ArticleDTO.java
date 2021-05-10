package com.ftn.osa.model.dto;

import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.Customer;
import com.ftn.osa.model.entity.Discount;
import com.ftn.osa.model.entity.Seller;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class ArticleDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @Positive
    private String price;

    @NotBlank
    private String path;

    private Set<Discount> discounts = new HashSet<>();

    private Seller seller;


    public ArticleDTO(Article article) {
        this.id = article.getId();
        this.description = article.getDescription();
        this.name = article.getName();
        this.path = article.getPath();
        this.price = article.getPrice();
        this.seller = article.getSeller();
        this.discounts = article.getDiscounts();
    }
}
