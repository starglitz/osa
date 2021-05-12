package com.ftn.osa.rest.impl;

import com.ftn.osa.model.dto.ArticleDTO;
import com.ftn.osa.model.dto.SellerListDTO;
import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.Discount;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.rest.ArticleApi;
import com.ftn.osa.service.ArticleService;
import com.ftn.osa.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.security.Principal;

@Component
public class ArticleApiImpl implements ArticleApi {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SellerService sellerService;

    @Override
    public ResponseEntity getAllArticles() {
        return new ResponseEntity(articleService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getArticle(Long id) {

        Article article = articleService.getArticle(id);

        if(article != null) {
            return new ResponseEntity(new ArticleDTO(article), HttpStatus.OK);
        }
        return new ResponseEntity("Article not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity getArticlesByCurrentSeller(Authentication authentication) {
        System.out.println(" entered api method!!!!!");
        return new ResponseEntity(articleService.findAllByCurrentSeller(authentication), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getArticlesBySeller(Long id) {
        return new ResponseEntity(articleService.findAllBySellerId(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Article> update(Long id,@Valid ArticleDTO articleDTO) {
        Seller seller = sellerService.getById(articleDTO.getSeller().getId());
        Article article = new Article(articleDTO.getId(), articleDTO.getName(),
                articleDTO.getDescription(), articleDTO.getPrice(), articleDTO.getPath(),
                articleDTO.getDiscounts(), seller);

        return new ResponseEntity(articleService.update(article), HttpStatus.OK);
    }

    @Override
    public ResponseEntity delete(Long id) {
        articleService.delete(id);
        return new ResponseEntity("Successfuly deleted", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Article> create(@Valid ArticleDTO articleDTO, Authentication authentication) {

        Article article = new Article(articleDTO.getId(), articleDTO.getName(),
                articleDTO.getDescription(), articleDTO.getPrice(), articleDTO.getPath(),
                articleDTO.getDiscounts());
        return new ResponseEntity(articleService.create(article, authentication), HttpStatus.OK);
    }
}
