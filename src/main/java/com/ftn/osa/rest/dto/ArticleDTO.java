package com.ftn.osa.rest.dto;

import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.Discount;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        for(Discount discount : article.getDiscounts()) {
            if((discount.getDateFrom().isBefore(now) || discount.getDateFrom().equals(now))
            && (discount.getDateTo().isAfter(now) || discount.getDateTo().isEqual(now)))
            discounts += discount.getPercent();
        }
        if(discounts > 60) {
            this.discounts = 60;
        }

    }

    public static ArticleDTO fromEntity(Article entity) {
        ArticleDTO article = new ArticleDTO();
        article.id = entity.getId();
        article.description = entity.getDescription();
        article.name = entity.getName();
        article.path = entity.getPath();
        article.price = entity.getPrice();
        if(entity.getSeller() != null) {
            article.seller = new SellerListDTO(entity.getSeller());
        }

        LocalDate now = LocalDate.now();

        for(Discount discount : entity.getDiscounts()) {
            if((discount.getDateFrom().isBefore(now) || discount.getDateFrom().equals(now))
                    && (discount.getDateTo().isAfter(now) || discount.getDateTo().isEqual(now)))
                article.discounts += discount.getPercent();
        }
        if(article.discounts > 60) {
            article.discounts = 60;
        }

        return article;
    }

    public static List<ArticleDTO> fromEntityList(List<Article> articles) {
        return articles.stream().map(ArticleDTO::fromEntity).collect(Collectors.toList());
    }
}
