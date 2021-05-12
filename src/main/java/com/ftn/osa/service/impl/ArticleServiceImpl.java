package com.ftn.osa.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftn.osa.model.dto.ArticleDTO;
import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.model.entity.User;
import com.ftn.osa.repository.ArticleRepository;
import com.ftn.osa.repository.SellerRepository;
import com.ftn.osa.repository.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public List<ArticleDTO> findAll() {

        List<Article> articles = articleRepository.findAll();
        List<ArticleDTO> articleDtos = new ArrayList<>();
        for(Article article : articles) {
            ArticleDTO articleDTO = new ArticleDTO(article);
            articleDtos.add(articleDTO);
        }

        return articleDtos;
    }

    @Override
    public List<ArticleDTO> findAllByCurrentSeller(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        String username = userPrincipal.getUsername();

        List<Article> articles = articleRepository.getArticlesByCurrentSellerUsername(username);
        List<ArticleDTO> articleDtos = new ArrayList<>();
        for(Article article : articles) {
            ArticleDTO articleDTO = new ArticleDTO(article);
            articleDtos.add(articleDTO);
        }

        return articleDtos;
    }

    @Override
    public List<ArticleDTO> findAllBySellerId(Long id) {
        List<Article> articles = articleRepository.getArticlesBySellerId(id);
        List<ArticleDTO> articleDtos = new ArrayList<>();
        for(Article article : articles) {
            ArticleDTO articleDTO = new ArticleDTO(article);
            articleDtos.add(articleDTO);
        }

        return articleDtos;
    }

    @Override
    @JsonIgnore
    public Article getArticle(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    @Override
    public Article update(Article article) {
       // Appointment appointmentJpa = findById(appointment.getAppointment_id()).get();
        Article articleJpa = getArticle(article.getId());

        articleJpa.setName(article.getName());
        articleJpa.setDescription(article.getDescription());
        articleJpa.setPrice(article.getPrice());
        articleJpa.setPath(article.getPath());

        return articleRepository.save(articleJpa);
    }

    @Override
    public void delete(Long id) {
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

        return articleRepository.save(article);
    }




}
