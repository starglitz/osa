package com.ftn.osa.service.impl;

import com.ftn.osa.OsaApplication;
import com.ftn.osa.model.dto.OrderDTO;
import com.ftn.osa.model.dto.SellerDTO;
import com.ftn.osa.model.dto.SellerListDTO;
import com.ftn.osa.model.entity.Order;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.model.entity.User;
import com.ftn.osa.repository.SellerRepository;
import com.ftn.osa.repository.UserRepository;
import com.ftn.osa.service.OrderService;
import com.ftn.osa.service.SellerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    static Logger log = Logger.getLogger(OsaApplication.class.getName());

    @Autowired
    private SellerRepository sellerRepository;

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
    public Seller getLoggedIn(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        //System.out.println("TRENUTNI ULOGOVANI usernname =" + userPrincipal.getUsername());
        String username = userPrincipal.getUsername();
        Seller seller = sellerRepository.findByUsername(username).get();
        //SellerDTO sellerDTO = new SellerDTO(seller);
        return seller;
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
    public Seller createSeller(Seller seller) {
        Optional<User> user = userRepository.findFirstByUsername(seller.getUser().getUsername());

        if(user.isPresent()){
            return null;
        }

        User userJpa = userRepository.save(seller.getUser());

        seller.setUser(userJpa);
        seller.setId(userJpa.getId());
        seller = sellerRepository.save(seller);
        return seller;
    }


    @Override
    public Seller getById(Long id) {
        return sellerRepository.findById(id).orElse(null);
    }


    @Override
    public double findAverageSellerRating(Long sellerId) {
        List<Order> sellersOrders = orderService.findBySellerId(sellerId);

        List<Integer> ratings = new ArrayList<>();
        for(Order order : sellersOrders) {
            if (order.isDelivered() == true && order.getRating() != 0) {
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
