package com.ftn.osa.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

import java.util.List;

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
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public List<Article> findAllByCurrentSeller(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        System.out.println("TRENUTNI ULOGOVANI usernname =" + userPrincipal.getUsername());
        String username = userPrincipal.getUsername();
        return articleRepository.getArticlesByCurrentSellerUsername(username);
    }

    @Override
    public List<Article> findAllBySellerId(Long id) {
        return articleRepository.getArticlesBySellerId(id);
    }

    @Override
    @JsonIgnore
    public Article getArticle(Long id) {
        return articleRepository.findById(id).get();
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

    @Override
    public Article findById(Long id) {
        return articleRepository.findById(id).get();
    }


}
