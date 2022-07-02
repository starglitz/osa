package com.ftn.osa.rest.dto;

import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.Discount;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class DiscountDTO {

    private Long id;

    @NotNull
    private int percent;

    @NotNull
    private LocalDate dateFrom;

    @NotNull
    @Future
    private LocalDate dateTo;

    @NotBlank
    private String description;

    private SellerListDTO seller;

    private List<ArticleDTO> articles = new ArrayList<>();

    public DiscountDTO(Discount discount) {
        this.id = discount.getId();
        this.percent = discount.getPercent();
        this.dateFrom = discount.getDateFrom();
        this.dateTo = discount.getDateTo();
        this.description = discount.getDescription();
        this.seller = new SellerListDTO(discount.getSeller());
        ArrayList<ArticleDTO> articleDTOS = new ArrayList<>();
        for(Article article : discount.getArticles()) {
            articleDTOS.add(new ArticleDTO(article));
        }
        this.articles = articleDTOS;
    }

    public static List<DiscountDTO> fromEntityList(List<Discount> discounts) {
        return discounts.stream().map(DiscountDTO::new).collect(Collectors.toList());
    }


}
