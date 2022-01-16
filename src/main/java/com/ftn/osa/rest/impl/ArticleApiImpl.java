package com.ftn.osa.rest.impl;

import com.ftn.osa.model.dto.ArticleDTO;
import com.ftn.osa.model.dto.DiscountDTO;
import com.ftn.osa.model.dto.SellerListDTO;
import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.Discount;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.model.es.ArticleES;
import com.ftn.osa.rest.ArticleApi;
import com.ftn.osa.service.ArticleService;
import com.ftn.osa.service.DiscountService;
import com.ftn.osa.service.SellerService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ArticleApiImpl implements ArticleApi {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SellerService sellerService;


    @Override
    public ResponseEntity getAllArticles(String search) {
        List<ArticleES> articles = articleService.findAll(search);
        List<ArticleDTO> articleDtos = new ArrayList<>();
        for(ArticleES article : articles) {
            ArticleDTO articleDTO = new ArticleDTO(article);
            articleDtos.add(articleDTO);
        }
        return new ResponseEntity(articleDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getArticle(Long id) {

        Article article = articleService.getArticle(id);

        if(article != null) {
            return new ResponseEntity(new ArticleDTO(article), HttpStatus.OK);
        }
        return new ResponseEntity("Article not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity getArticlesByCurrentSeller(Authentication authentication) {

        List<Article> articles = articleService.findAllByCurrentSeller(authentication);
        List<ArticleDTO> articleDtos = new ArrayList<>();
        for(Article article : articles) {
            ArticleDTO articleDTO = new ArticleDTO(article);
            articleDtos.add(articleDTO);
        }
        System.out.println(" entered api method!!!!!");


        return new ResponseEntity(articleDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getArticlesBySeller(Long id) {
        List<Article> articles = articleService.findAllBySellerId(id);
        List<ArticleDTO> articleDtos = new ArrayList<>();
        for(Article article : articles) {
            ArticleDTO articleDTO = new ArticleDTO(article);
            articleDtos.add(articleDTO);
        }
        return new ResponseEntity(articleDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity update(Long id,@Valid ArticleDTO articleDTO) {

//        Seller seller = sellerService.getById(articleDTO.getSeller().getId());
//        if(seller == null) {
//            return new ResponseEntity("No such seller! ", HttpStatus.BAD_REQUEST);
//        }

//        Article articletest = articleService.getArticle(articleDTO.getId());
//        if(articletest == null) {
//            return new ResponseEntity("No article with such id!", HttpStatus.BAD_REQUEST);
//        }


        Article article = new Article(id, articleDTO.getName(),
                articleDTO.getDescription(), articleDTO.getPrice(), articleDTO.getPath());

        Article update = articleService.update(article);
        if(update == null) {
            return new ResponseEntity("No article with such id!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(articleService.update(article), HttpStatus.OK);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Article article = articleService.getArticle(id);
        if(article == null) {
            return new ResponseEntity("No article with chosen id", HttpStatus.NOT_FOUND);
        }
        articleService.delete(id);
        return new ResponseEntity("Successfuly deleted", HttpStatus.OK);
    }

    @Override
    public ResponseEntity create(@Valid ArticleDTO articleDTO, Authentication authentication) {
//@NotBlank String name, @NotBlank String description,
//                   @NotNull @Positive int price, @NotBlank String path
        Article article = new Article(articleDTO.getName(),
                articleDTO.getDescription(), articleDTO.getPrice(), articleDTO.getPath());

        Article create = articleService.create(article, authentication);

        return new ResponseEntity(articleDTO, HttpStatus.OK);
    }
}
