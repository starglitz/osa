package com.ftn.osa.rest.impl;

import com.ftn.osa.rest.dto.ArticleDTO;
import com.ftn.osa.rest.ArticleApi;
import com.ftn.osa.service.ArticleService;
import com.ftn.osa.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

@Component
public class ArticleApiImpl implements ArticleApi {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SellerService sellerService;


    @Override
    public ResponseEntity getAllArticles() {
        List<ArticleDTO> articles = articleService.findAll();
        return new ResponseEntity(articles, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getArticle(Long id) {

        ArticleDTO article = articleService.getArticle(id);

        if(article != null) {
            return new ResponseEntity(article, HttpStatus.OK);
        }
        return new ResponseEntity("Article not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity getArticlesByCurrentSeller(Authentication authentication) {
        List<ArticleDTO> articles = articleService.findAllByCurrentSeller(authentication);
        return new ResponseEntity(articles, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getArticlesBySeller(Long id) {
        List<ArticleDTO> articles = articleService.findAllBySellerId(id);
        return new ResponseEntity(articles, HttpStatus.OK);
    }

    @Override
    public ResponseEntity update(Long id, @Valid ArticleDTO articleDTO) {

        ArticleDTO update = articleService.update(articleDTO);
        if(update == null) {
            return new ResponseEntity("No article with such id!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(update, HttpStatus.OK);
    }

    @Override
    public ResponseEntity delete(Long id) {
        ArticleDTO article = articleService.getArticle(id);
        if(article == null) {
            return new ResponseEntity("No article with chosen id", HttpStatus.NOT_FOUND);
        }
        articleService.delete(id);
        return new ResponseEntity("Successfuly deleted", HttpStatus.OK);
    }

    @Override
    public ResponseEntity create(@Valid ArticleDTO articleDTO, Authentication authentication) {
        ArticleDTO created = articleService.create(articleDTO, authentication);
        return new ResponseEntity(created, HttpStatus.OK);
    }
}
