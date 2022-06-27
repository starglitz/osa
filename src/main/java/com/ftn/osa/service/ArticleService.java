package com.ftn.osa.service;

import com.ftn.osa.model.entity.Article;
import com.ftn.osa.rest.dto.ArticleDTO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ArticleService {

    public List<ArticleDTO> findAll();

    public List<ArticleDTO> findAllByCurrentSeller(Authentication authentication);

    public List<ArticleDTO> findAllBySellerId(Long id);

    public ArticleDTO getArticle(Long id);

    public Article getArticleEntity(Long id);

    public ArticleDTO update(ArticleDTO article);

    public void delete(Long id);

    public ArticleDTO create(ArticleDTO article, Authentication authentication);


}
