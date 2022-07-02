package com.ftn.osa.rest.impl;

import com.ftn.osa.rest.dto.ArticleDTO;
import com.ftn.osa.service.ArticleService;
import com.ftn.osa.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SellerService sellerService;

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getAllArticles() {
        List<ArticleDTO> articles = articleService.findAll();
        return new ResponseEntity(articles, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN', 'CUSTOMER')")
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getArticle(@PathVariable("id") Long id) {

        ArticleDTO article = articleService.getArticle(id);

        if(article != null) {
            return new ResponseEntity(article, HttpStatus.OK);
        }
        return new ResponseEntity("Article not found", HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping(value = "/seller/me",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getArticlesByCurrentSeller(Authentication authentication) {
        List<ArticleDTO> articles = articleService.findAllByCurrentSeller(authentication);
        return new ResponseEntity(articles, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping(value = "/seller/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getArticlesBySeller(@PathVariable("id") Long id) {
        List<ArticleDTO> articles = articleService.findAllBySellerId(id);
        return new ResponseEntity(articles, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PutMapping(value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity update(@PathVariable("id") Long id, @Valid ArticleDTO articleDTO) {

        ArticleDTO update = articleService.update(articleDTO);
        if(update == null) {
            return new ResponseEntity("No article with such id!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(update, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SELLER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        ArticleDTO article = articleService.getArticle(id);
        if(article == null) {
            return new ResponseEntity("No article with chosen id", HttpStatus.NOT_FOUND);
        }
        articleService.delete(id);
        return new ResponseEntity("Successfuly deleted", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity create(@Valid @RequestBody ArticleDTO articleDTO, Authentication authentication) throws URISyntaxException {
        ArticleDTO created = articleService.create(articleDTO, authentication);
        return ResponseEntity
                .created(new URI("/articles/" + created.getId()))
                .body(created);
    }
}
