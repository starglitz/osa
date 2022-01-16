package com.ftn.osa.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.Customer;
import com.ftn.osa.model.entity.Discount;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.model.es.ArticleES;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class ArticleDTO {

    private Long id;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String description;

    @NotNull
    @Positive
    private int price;

    @NotBlank
    @NotNull
    private String path;


    private int discounts;

    private SellerListDTO seller;


    public ArticleDTO(Article article) {
        this.id = article.getId();
        this.description = article.getDescription();
        this.name = article.getName();
        this.path = article.getPath();
        this.price = article.getPrice();
        if(article.getSeller() != null) {
            this.seller = new SellerListDTO(article.getSeller());
        }
        List<DiscountDTO> discountDTOS = new ArrayList<>();

        LocalDate now = LocalDate.now();

//        for(Discount discount : article.getDiscounts()) {
//            if((discount.getDateFrom().isBefore(now) || discount.getDateFrom().equals(now))
//            && (discount.getDateTo().isAfter(now) || discount.getDateTo().isEqual(now)))
//            discounts += discount.getPercent();
//        }
//        if(discounts > 60) {
//            this.discounts = 60;
//        }

    }

    public ArticleDTO(ArticleES article) {
        this.id = article.getId();
        this.description = article.getDescription();
        this.name = article.getName();
        this.path = article.getPath();
        this.price = article.getPrice();
        if(article.getSeller() != null) {
            this.seller = new SellerListDTO(article.getSeller());
        }
        List<DiscountDTO> discountDTOS = new ArrayList<>();

        LocalDate now = LocalDate.now();

//        for(Discount discount : article.getDiscounts()) {
//            if((discount.getDateFrom().isBefore(now) || discount.getDateFrom().equals(now))
//            && (discount.getDateTo().isAfter(now) || discount.getDateTo().isEqual(now)))
//            discounts += discount.getPercent();
//        }
//        if(discounts > 60) {
//            this.discounts = 60;
//        }

    }
}
