package com.ftn.osa.service.impl;

import com.ftn.osa.model.dto.ArticleDTO;
import com.ftn.osa.model.dto.DiscountDTO;
import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.Discount;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.repository.ArticleRepository;
import com.ftn.osa.repository.DiscountRepository;
import com.ftn.osa.repository.SellerRepository;
import com.ftn.osa.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public boolean addDiscount(DiscountDTO discountDTO, Authentication authentication) {

        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        String username = userPrincipal.getUsername();
        Seller seller = sellerRepository.findByUsername(username).orElse(null);

        if(seller == null) {
            return false;
        }

        List<Article> articles = new ArrayList<>();

        for(ArticleDTO article : discountDTO.getArticles()) {
            Article articleJpa = articleRepository.findById(article.getId()).orElse(null);
            if(articleJpa != null) {
                articles.add(articleJpa);
            }
        }

        if(discountDTO.getArticles().size() != articles.size()) {
            return false;
        }

        Discount discount = new Discount(discountDTO.getPercent(), discountDTO.getDateFrom(),
                discountDTO.getDateTo(), discountDTO.getDescription(), seller,
                articles);

        discountRepository.save(discount);
        return true;
    }
}
