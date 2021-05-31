package com.ftn.osa.rest.impl;

import com.ftn.osa.model.dto.ArticleDTO;
import com.ftn.osa.model.dto.DiscountDTO;
import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.Discount;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.rest.DiscountApi;
import com.ftn.osa.service.ArticleService;
import com.ftn.osa.service.DiscountService;
import com.ftn.osa.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Component
public class DiscountApiImpl implements DiscountApi {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SellerService sellerService;

    @Override
    public ResponseEntity add(@Valid DiscountDTO discountDTO, Authentication authentication) {

        List<Article> articles = new ArrayList<>();

        if(discountDTO.getArticles().size() == 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }


        for(ArticleDTO article : discountDTO.getArticles()) {
            Article articleJpa = articleService.getArticle(article.getId());
            if(articleJpa != null) {
                articles.add(articleJpa);
            }
        }

        Seller seller = sellerService.getLoggedIn(authentication);

        Discount discount = new Discount(discountDTO.getPercent(), discountDTO.getDateFrom(),
                discountDTO.getDateTo(), discountDTO.getDescription(), seller,
                articles);

        discount = discountService.addDiscount(discount);

        return new ResponseEntity(discountDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getByCurrentSeller(Authentication authentication) {
        List<Discount> discounts = discountService.getByCurrentSeller(authentication);
        List<DiscountDTO> discountDTOS = new ArrayList<>();
        for(Discount discount : discounts) {
            DiscountDTO discountDTO = new DiscountDTO(discount);
            discountDTOS.add(discountDTO);
        }
        return new ResponseEntity(discountDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity delete(Long id) {
        discountService.delete(id);
        return new ResponseEntity("Successfully deleted", HttpStatus.OK);
    }

    @Override
    public ResponseEntity get(Long id) {
        Discount discount = discountService.findById(id);
        if(discount == null) {
            return new ResponseEntity("No such discount", HttpStatus.NOT_FOUND);
        }
        DiscountDTO dto = new DiscountDTO(discount);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity update(Long id, @Valid DiscountDTO discountDTO) {

        Discount discount = discountService.findById(id);
        if(discount == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        List<Article> articles = new ArrayList<>();

        for(ArticleDTO article : discountDTO.getArticles()) {
            Article articleJpa = articleService.getArticle(article.getId());
            if(articleJpa != null) {
                articles.add(articleJpa);
            }
        }

        Discount update = new Discount(id, discountDTO.getPercent(), discountDTO.getDateFrom(),
                discountDTO.getDateTo(), discountDTO.getDescription(), articles);

        Discount updatedJpa = discountService.update(update);
        return  new ResponseEntity(discountDTO, HttpStatus.OK);
    }
}
