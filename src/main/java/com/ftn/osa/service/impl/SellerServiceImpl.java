package com.ftn.osa.service.impl;

import com.ftn.osa.OsaApplication;
import com.ftn.osa.model.entity.Order;
import com.ftn.osa.model.entity.Role;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.model.entity.User;
import com.ftn.osa.repository.SellerRepository;
import com.ftn.osa.repository.UserRepository;
import com.ftn.osa.rest.dto.SellerDTO;
import com.ftn.osa.rest.dto.SellerListDTO;
import com.ftn.osa.service.OrderService;
import com.ftn.osa.service.SellerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    static Logger log = Logger.getLogger(OsaApplication.class.getName());

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrderService orderService;

    @Override
    public List<Seller> getAll() {
        return sellerRepository.findAll();
    }

    @Override
    public List<SellerListDTO> getAllDto() {
        List<Seller> sellers = this.getAll();

        List<SellerListDTO> sellersForShow = new ArrayList<>();
        for(Seller seller : sellers) {
            SellerListDTO sellerForShow = new SellerListDTO(seller);

            double rating = sellerService.findAverageSellerRating(seller.getId());
            sellerForShow.setRating(rating);
            sellersForShow.add(sellerForShow);
        }
        return sellersForShow;
    }

    @Override
    public Seller getLoggedIn(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        String username = userPrincipal.getUsername();
        Seller seller = sellerRepository.findByUsername(username).get();
        //SellerDTO sellerDTO = new SellerDTO(seller);
        return seller;
    }

    @Override
    public boolean update(SellerDTO sellerDTO) {


        User user = new User(sellerDTO.getId(), sellerDTO.getName(), sellerDTO.getSurname(),
                sellerDTO.getUsername(), sellerDTO.getPassword(), true, Role.SELLER);

        Seller seller = new Seller(sellerDTO.getSince(), sellerDTO.getEmail(),
                sellerDTO.getAddress(), sellerDTO.getSellerName(), user, sellerDTO.getId());

        boolean ok = true;
        User userJpa = userRepository.findById(seller.getId()).get();

        userJpa.setName(seller.getUser().getName());
        userJpa.setSurname(seller.getUser().getSurname());
        userJpa.setUsername(seller.getUser().getUsername());

        if(passwordEncoder.matches(sellerDTO.getPasswordValidate(),
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
    public SellerDTO createSeller(SellerDTO sellerDTO) {

        Optional<User> user = userRepository.findFirstByUsername(sellerDTO.getUsername());

        if(user.isPresent()){
            return null;
        }

        Optional<Seller> sellerJpa = sellerRepository.findByEmail(sellerDTO.getEmail());
        if(sellerJpa.isPresent()) {
            return null;
        };

        User newUser = new User(sellerDTO.getName(), sellerDTO.getSurname(),
                sellerDTO.getUsername(), passwordEncoder.encode(sellerDTO.getPassword()),
                true, Role.SELLER);

        Seller newSeller = new Seller(new Date(), sellerDTO.getEmail(), sellerDTO.getAddress(),
                sellerDTO.getSellerName(), newUser);

        User userJpa = userRepository.save(newSeller.getUser());

        newSeller.setUser(userJpa);
        newSeller.setId(userJpa.getId());
        newSeller = sellerRepository.save(newSeller);
        return new SellerDTO(newSeller);
    }


    @Override
    public Seller getById(Long id) {
        return sellerRepository.findById(id).orElse(null);
    }

    @Override
    public SellerListDTO getDTOById(Long id) {
        Seller seller = sellerService.getById(id);

        if(seller != null) {
            double rating = sellerService.findAverageSellerRating(id);
            SellerListDTO dto = new SellerListDTO(seller);
            dto.setRating(rating);
            return dto;
        }
        return null;
    }


    @Override
    public double findAverageSellerRating(Long sellerId) {
        List<Order> sellersOrders = orderService.findBySellerId(sellerId);

        List<Integer> ratings = new ArrayList<>();
        for(Order order : sellersOrders) {
            if (order.isDelivered() && order.getRating() != 0) {
                ratings.add(order.getRating());
            }
        }


        return calculateAverage(ratings);
    }


    private double calculateAverage(List <Integer> ratings) {
        return ratings.stream()
                .mapToDouble(d -> d)
                .average()
                .orElse(0.0);
    }
}
