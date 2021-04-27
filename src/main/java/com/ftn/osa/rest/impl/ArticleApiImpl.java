package com.ftn.osa.rest.impl;

import com.ftn.osa.rest.ArticleApi;
import com.ftn.osa.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ArticleApiImpl implements ArticleApi {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseEntity getAllArticles() {
        return new ResponseEntity(articleService.findAll(), HttpStatus.OK);
    }
}
