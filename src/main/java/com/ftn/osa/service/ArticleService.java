package com.ftn.osa.service;

import com.ftn.osa.model.dto.ArticleDTO;
import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.es.ArticleES;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {

    public List<ArticleES> findAll(String name);

    public List<ArticleES> findByPriceRange(int start, int end);

    public List<Article> findAllByCurrentSeller(Authentication authentication);

    public List<Article> findAllBySellerId(Long id);

    public Article getArticle(Long id);

    public Article update(Article article);

    public void delete(Long id);

    public Article create(Article article, Authentication authentication);


}
