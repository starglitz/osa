package com.ftn.osa.rest.impl;

import com.ftn.osa.rest.dto.DiscountDTO;
import com.ftn.osa.model.entity.Discount;
import com.ftn.osa.service.ArticleService;
import com.ftn.osa.service.DiscountService;
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
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SellerService sellerService;

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity add(
            @Valid @RequestBody DiscountDTO discountDTO,
            Authentication authentication
    ) throws URISyntaxException {

        if(discountDTO.getArticles().size() == 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        DiscountDTO dto = discountService.addDiscount(discountDTO, authentication);
        return ResponseEntity
                .created(new URI("/articles/" + dto.getId()))
                .body(dto);
    }

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping(value = "/seller/me",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getByCurrentSeller(Authentication authentication) {
        List<Discount> discounts = discountService.getByCurrentSeller(authentication);
        return new ResponseEntity(DiscountDTO.fromEntityList(discounts), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SELLER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        discountService.delete(id);
        return new ResponseEntity("Successfully deleted", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity get(@PathVariable("id") Long id) {
        DiscountDTO discount = discountService.findById(id);
        if(discount == null) {
            return new ResponseEntity("No such discount", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(discount, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    @PutMapping(value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody DiscountDTO discountDTO) {

        DiscountDTO update = discountService.update(discountDTO);
        if(update == null) {
            return new ResponseEntity("No such discount", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(update, HttpStatus.OK);
    }
}
