package com.ftn.osa.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftn.osa.OsaApplication;
import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.model.entity.User;
import com.ftn.osa.repository.ArticleRepository;
import com.ftn.osa.repository.SellerRepository;
import com.ftn.osa.repository.UserRepository;
import com.ftn.osa.rest.dto.ArticleDTO;
import com.ftn.osa.security.TokenUtils;
import com.ftn.osa.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public List<ArticleDTO> findAll() {

        List<Article> articles = articleRepository.findAll();
        return ArticleDTO.fromEntityList(articles);
    }

    @Override
    public List<ArticleDTO> findAllByCurrentSeller(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        String username = userPrincipal.getUsername();

        List<Article> articles = articleRepository.getArticlesByCurrentSellerUsername(username);


        return ArticleDTO.fromEntityList(articles);
    }

    @Override
    public List<ArticleDTO> findAllBySellerId(Long id) {
        List<Article> articles = articleRepository.getArticlesBySellerId(id);
        return ArticleDTO.fromEntityList(articles);
    }

    @Override
    @JsonIgnore
    public ArticleDTO getArticle(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        return article.map(ArticleDTO::fromEntity).orElse(null);
    }

    @Override
    public Article getArticleEntity(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    @Override
    public ArticleDTO update(ArticleDTO article) {

        Article articleJpa = getArticleEntity(article.getId());

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
        return ArticleDTO.fromEntity(save);
    }

    @Override
    public void delete(Long id) {
        OsaApplication.log.info("Deleted article with ID " + id);
        articleRepository.deleteById(id);
    }

    @Override
    public ArticleDTO create(ArticleDTO articleDTO, Authentication authentication) {

        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        String username = userPrincipal.getUsername();
        User user = userRepository.findFirstByUsername(username).get();
        Seller seller = sellerRepository.findById(user.getId()).get();

        Article article = new Article(articleDTO.getName(),
                articleDTO.getDescription(), articleDTO.getPrice(), articleDTO.getPath());
        article.setSeller(seller);
        Article save = articleRepository.save(article);
        OsaApplication.log.info("Created new article");
        return ArticleDTO.fromEntity(save);
    }




}
