package com.ftn.osa.config;

import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.es.ArticleES;
import com.ftn.osa.repository.ArticleRepository;
import com.ftn.osa.searchRepository.ArticleSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class InjectDataToElastic {

   @Autowired
    private ArticleSearchRepository articleSearchRepository;

   @Autowired
    private ArticleRepository articleRepository;


    @PostConstruct
    @Transactional
    public void init() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleES> es = ArticleES.fromArticleList(articles);
        for(ArticleES article : es) {
            articleSearchRepository.save(article);
        }
    }
}
