package com.ftn.osa.model.es;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.Discount;
import com.ftn.osa.model.entity.Seller;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document(indexName = "books")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class ArticleES {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Integer)
    private int price;

    @Field(type = FieldType.Text)
    private String path;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Discount> discounts = new ArrayList<>();

    @Field(type = FieldType.Nested, includeInParent = true)
    private Seller seller;

    public ArticleES(Long id, @NotBlank String name, @NotBlank String description,
                   @NotNull @Positive int price, @NotBlank String path,
                   List<Discount> discounts, Seller seller) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.path = path;
        this.discounts = discounts;
        this.seller = seller;
    }

    public ArticleES(Long id, @NotBlank String name, @NotBlank String description,
                   @NotNull @Positive int price, @NotBlank String path,
                   List<Discount> discounts) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.path = path;
        this.discounts = discounts;
    }

    public ArticleES(Long id, @NotBlank String name, @NotBlank String description,
                   @NotNull @Positive int price, @NotBlank String path) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.path = path;
    }

    public ArticleES(Long id, @NotBlank String name, @NotBlank String description,
                   @NotNull @Positive int price, @NotBlank String path, Seller seller) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.path = path;
        this.seller = seller;
    }

    public ArticleES(@NotBlank String name, @NotBlank String description,
                   @NotNull @Positive int price, @NotBlank String path) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.path = path;
    }

    public static ArticleES fromArticle(Article article) {
        return new ArticleES(article.getId(), article.getName(), article.getDescription(),
                article.getPrice(), article.getPath(), article.getSeller());
    }

    public static List<ArticleES> fromArticleList(List<Article> articles) {
        List<ArticleES> es = new ArrayList<>();
        for(Article article : articles) {
            es.add(fromArticle(article));
        }
        return es;
    }
}
