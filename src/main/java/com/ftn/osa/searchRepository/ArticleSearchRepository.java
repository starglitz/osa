package com.ftn.osa.searchRepository;

import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.es.ArticleES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleSearchRepository extends ElasticsearchRepository<ArticleES, Long> {
    List<ArticleES> findAllByName(String name);
}
