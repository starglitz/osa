package com.ftn.osa.service.impl;

import com.ftn.osa.model.entity.Article;
import com.ftn.osa.repository.ArticleRepository;
import com.ftn.osa.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }
}
