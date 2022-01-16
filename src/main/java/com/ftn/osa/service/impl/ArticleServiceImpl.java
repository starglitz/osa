package com.ftn.osa.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftn.osa.OsaApplication;
import com.ftn.osa.model.dto.ArticleDTO;
import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.model.entity.User;
import com.ftn.osa.model.es.ArticleES;
import com.ftn.osa.repository.ArticleRepository;
import com.ftn.osa.repository.SellerRepository;
import com.ftn.osa.repository.UserRepository;
import com.ftn.osa.searchRepository.ArticleSearchRepository;
import com.ftn.osa.security.TokenUtils;
import com.ftn.osa.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleSearchRepository articleSearchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public List<ArticleES> findAll(String name) {
        System.out.println("NAME: " + name);
        List<ArticleES> l = new ArrayList<>();
        if(!name.equals("")) {
            return articleSearchRepository.findAllByName(name);
        }
        Iterable<ArticleES> articles = articleSearchRepository.findAll();
        articles.forEach(l::add);
        System.out.println(articles);

        return l;
    }

    @Override
    public List<Article> findAllByCurrentSeller(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        String username = userPrincipal.getUsername();

        List<Article> articles = articleRepository.getArticlesByCurrentSellerUsername(username);
        return articles;
    }

    @Override
    public List<Article> findAllBySellerId(Long id) {
        List<Article> articles = articleRepository.getArticlesBySellerId(id);
        return articles;
    }

    @Override
    @JsonIgnore
    public Article getArticle(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    @Override
    public Article update(Article article) {

        Article articleJpa = getArticle(article.getId());

        if(articleJpa == null) {
            OsaApplication.log.info("Article an user is trying to update doesn't exist");
            return null;
        }

        articleJpa.setName(article.getName());
        articleJpa.setDescription(article.getDescription());
        articleJpa.setPrice(article.getPrice());
        articleJpa.setPath(article.getPath());

        Article save = articleRepository.save(articleJpa);
        OsaApplication.log.info("Successfully updated an article with ID " + article.getId());
        return save;
    }

    @Override
    public void delete(Long id) {
        OsaApplication.log.info("Deleted article with ID " + id);
        articleRepository.deleteById(id);
    }

    @Override
    public Article create(Article article, Authentication authentication) {

        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        System.out.println("TRENUTNI ULOGOVANI usernname =" + userPrincipal.getUsername());
        String username = userPrincipal.getUsername();
        User user = userRepository.findFirstByUsername(username).get();
        Seller seller = sellerRepository.findById(user.getId()).get();

        article.setSeller(seller);
        Article save = articleRepository.save(article);
        OsaApplication.log.info("Created new article");
        return article;
    }




}
