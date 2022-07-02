package com.ftn.osa.rest.impl;

import com.ftn.osa.rest.dto.ArticleDTO;
import com.ftn.osa.rest.dto.DiscountDTO;
import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.Discount;
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

        if(discountDTO.getArticles().size() == 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(discountService.addDiscount(discountDTO, authentication), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getByCurrentSeller(Authentication authentication) {
        List<Discount> discounts = discountService.getByCurrentSeller(authentication);
        return new ResponseEntity(DiscountDTO.fromEntityList(discounts), HttpStatus.OK);
    }

    @Override
    public ResponseEntity delete(Long id) {
        discountService.delete(id);
        return new ResponseEntity("Successfully deleted", HttpStatus.OK);
    }

    @Override
    public ResponseEntity get(Long id) {
        DiscountDTO discount = discountService.findById(id);
        if(discount == null) {
            return new ResponseEntity("No such discount", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(discount, HttpStatus.OK);
    }

    @Override
    public ResponseEntity update(Long id, @Valid DiscountDTO discountDTO) {

        DiscountDTO update = discountService.update(discountDTO);
        if(update == null) {
            return new ResponseEntity("No such discount", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(update, HttpStatus.OK);
    }
}
