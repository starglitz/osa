package com.ftn.osa.service.impl;

import com.ftn.osa.OsaApplication;
import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.Discount;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.repository.ArticleRepository;
import com.ftn.osa.repository.DiscountRepository;
import com.ftn.osa.repository.SellerRepository;
import com.ftn.osa.rest.dto.ArticleDTO;
import com.ftn.osa.rest.dto.DiscountDTO;
import com.ftn.osa.service.DiscountService;
import com.ftn.osa.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

    @Autowired
    private SellerService sellerService;

    @Override
    public DiscountDTO addDiscount(DiscountDTO discountDTO, Authentication authentication) {
        List<Article> articles = getArticlesOfDiscount(discountDTO);

        Seller seller = sellerService.getLoggedIn(authentication);

        Discount discount = new Discount(discountDTO.getPercent(), discountDTO.getDateFrom(),
                discountDTO.getDateTo(), discountDTO.getDescription(), seller,
                articles);

        return new DiscountDTO(discountRepository.save(discount));
    }

    @Override
    public List<Discount> getByCurrentSeller(Authentication authentication) {
        Seller seller = sellerService.getLoggedIn(authentication);
        return discountRepository.findBySellerId(seller.getId());
    }

    @Override
    public Discount findEntityById(Long id) {
        return discountRepository.findById(id).orElse(null);
    }

    @Override
    public DiscountDTO findById(Long id) {
        Discount discount = findEntityById(id);
        if(discount != null) {
            return new DiscountDTO(discount);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        OsaApplication.log.info("Discount with ID " + id + "deleted");
        discountRepository.deleteById(id);
    }

    private List<Article> getArticlesOfDiscount(DiscountDTO discountDTO) {
        final List<Article> articles = new ArrayList<>();
        for(ArticleDTO article : discountDTO.getArticles()) {
            Article articleJpa = articleRepository.getOne(article.getId());
            articles.add(articleJpa);
        }
        return articles;
    }

    @Override
    public DiscountDTO update(DiscountDTO discountDTO) {

        Discount discountJpa = this.findEntityById(discountDTO.getId());
        if(discountJpa == null) {
            return null;
        }

        List<Article> articles = getArticlesOfDiscount(discountDTO);

        Discount update = new Discount(discountDTO.getId(), discountDTO.getPercent(), discountDTO.getDateFrom(),
                discountDTO.getDateTo(), discountDTO.getDescription(), articles);


        Discount discount = discountRepository.findById(update.getId()).orElse(null);

        discount.setArticles(update.getArticles());
        discount.setPercent(update.getPercent());
        discount.setDateFrom(update.getDateFrom());
        discount.setDateTo(update.getDateTo());
        discount.setDescription(update.getDescription());
        OsaApplication.log.info("Discount with ID " + update.getId() + " successfully updated");

        return new DiscountDTO(discountRepository.save(discount));
    }
}
