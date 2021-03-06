package com.ftn.osa.rest;

import com.ftn.osa.model.dto.ArticleDTO;
import com.ftn.osa.model.entity.Article;
import org.apache.coyote.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/articles")
public interface ArticleApi {

    @PermitAll
    @GetMapping(value="/search",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getAllArticles(@RequestParam(defaultValue = "") String search);

    @GetMapping(value="/searchByRange")
    ResponseEntity getByPriceRange(@RequestParam(defaultValue = "0") int start,
                                   @RequestParam(defaultValue = "999999") int end);

    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN', 'CUSTOMER')")
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getArticle(@PathVariable("id") Long id);

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping(value = "/seller/me",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getArticlesByCurrentSeller(
            Authentication authentication
    );

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping(value = "/seller/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getArticlesBySeller(@PathVariable("id") Long id);

    @PreAuthorize("hasRole('SELLER')")
    @PutMapping(value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity update(@PathVariable("id") Long id,@Valid @RequestBody ArticleDTO articleDTO);

    @PreAuthorize("hasRole('SELLER')")
    @DeleteMapping(value = "/{id}")
    ResponseEntity delete(@PathVariable("id") Long id);

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity create(@Valid @RequestBody ArticleDTO articleDTO, Authentication authentication);

}
