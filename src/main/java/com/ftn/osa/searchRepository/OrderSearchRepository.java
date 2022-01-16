package com.ftn.osa.searchRepository;

import com.ftn.osa.model.es.ArticleES;
import com.ftn.osa.model.es.OrderES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface OrderSearchRepository extends ElasticsearchRepository<OrderES, Long> {
    List<OrderES> findAllByComment(String comment);
}
