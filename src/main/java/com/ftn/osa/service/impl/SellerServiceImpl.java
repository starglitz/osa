package com.ftn.osa.service.impl;

import com.ftn.osa.model.dto.OrderDTO;
import com.ftn.osa.model.dto.SellerDTO;
import com.ftn.osa.model.dto.SellerListDTO;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.model.entity.User;
import com.ftn.osa.repository.SellerRepository;
import com.ftn.osa.repository.UserRepository;
import com.ftn.osa.service.OrderService;
import com.ftn.osa.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrderService orderService;



    @Override
    public List<SellerListDTO> getAllSellerListDTO() {
        List<Seller> sellers = sellerRepository.findAll();

        List<SellerListDTO> sellersForShow = new ArrayList<>();
        for(Seller seller : sellers) {
            SellerListDTO sellerForShow = new SellerListDTO(seller);

            double rating = findAverageSellerRating(seller.getId());
            sellerForShow.setRating(rating);
            sellersForShow.add(sellerForShow);
        }

        return sellersForShow;
    }

    @Override
    public SellerDTO getLoggedIn(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        System.out.println("TRENUTNI ULOGOVANI usernname =" + userPrincipal.getUsername());
        String username = userPrincipal.getUsername();
        Seller seller = sellerRepository.findByUsername(username).get();
        SellerDTO sellerDTO = new SellerDTO(seller);
        return sellerDTO;
    }

    @Override
    public boolean update(Seller seller, String validatePassword) {

        boolean ok = true;
        User userJpa = userRepository.findById(seller.getId()).get();

        userJpa.setName(seller.getUser().getName());
        userJpa.setSurname(seller.getUser().getSurname());
        userJpa.setUsername(seller.getUser().getUsername());

//        if(passwordEncoder.matches(seller.getUser().getPassword(),
//                userJpa.getPassword()) ||
//                passwordEncoder.matches(passwordEncoder.encode(seller.getUser().getPassword()),
//                        userJpa.getPassword())) {
//            userJpa.setPassword(passwordEncoder.encode(seller.getUser().getPassword()));
//        }

        if(passwordEncoder.matches(validatePassword,
                userJpa.getPassword())) {
            userJpa.setPassword(passwordEncoder.encode(seller.getUser().getPassword()));
        }


        else {
            ok = false;
        }
        userRepository.save(userJpa);

        Seller sellerJpa = sellerRepository.findById(seller.getId()).get();

        sellerJpa.setUser(userJpa);
        sellerJpa.setSellerName(seller.getSellerName());
        sellerJpa.setEmail(seller.getEmail());
        sellerJpa.setAddress(seller.getAddress());
        sellerJpa.setSince(seller.getSince());

        sellerRepository.save(sellerJpa);

        return ok;
    }

    @Override
    public Seller getById(Long id) {
        return sellerRepository.findById(id).orElse(null);
    }


    @Override
    public double findAverageSellerRating(Long sellerId) {
        List<OrderDTO> sellersOrders = orderService.findBySellerId(sellerId);

        List<Integer> ratings = new ArrayList<>();
        for(OrderDTO order : sellersOrders) {
            if (order.isDelivered() == true && order.getRating() != 0) {
                ratings.add(order.getRating());
            }
        }

        System.out.println("Sellers ratings:");
        System.out.println(ratings);

        return calculateAverage(ratings);
    }


//    @Override
//    public List<Integer> findPreviousRatings(Long sellerId) {
//        List<OrderDTO> sellersOrders = findBySellerId(sellerId);
//
//        List<Integer> ratings = new ArrayList<>();
//        for(OrderDTO order : sellersOrders) {
//            ratings.add(order.getRating());
//        }
//
//        return ratings;
//    }

    private double calculateAverage(List <Integer> ratings) {
        return ratings.stream()
                .mapToDouble(d -> d)
                .average()
                .orElse(0.0);
    }
}
