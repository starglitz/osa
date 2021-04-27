package com.ftn.osa.service;

import com.ftn.osa.model.entity.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {

    public List<Article> findAll();

}
