package com.ftn.osa.rest.impl;

import com.ftn.osa.model.entity.Article;
import com.ftn.osa.rest.ArticleApi;
import com.ftn.osa.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class ArticleApiImpl implements ArticleApi {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseEntity getAllArticles() {
        return new ResponseEntity(articleService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getArticle(Long id) {
        return new ResponseEntity(articleService.getArticle(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getArticlesByCurrentSeller(Authentication authentication) {
        System.out.println(" entered api method!!!!!");
        return new ResponseEntity(articleService.findAllByCurrentSeller(authentication), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Article> update(Long id,Article article) {
        return new ResponseEntity(articleService.update(article), HttpStatus.OK);
    }

    @Override
    public ResponseEntity delete(Long id) {
        articleService.delete(id);
        return new ResponseEntity("Successfuly deleted", HttpStatus.OK);
    }
}
