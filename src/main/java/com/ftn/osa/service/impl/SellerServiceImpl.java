package com.ftn.osa.service.impl;

import com.ftn.osa.model.dto.SellerDTO;
import com.ftn.osa.model.dto.SellerListDTO;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.model.entity.User;
import com.ftn.osa.repository.SellerRepository;
import com.ftn.osa.repository.UserRepository;
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



    @Override
    public List<SellerListDTO> getAllSellerListDTO() {
        List<Seller> sellers = sellerRepository.findAll();

        List<SellerListDTO> sellersForShow = new ArrayList<>();
        for(Seller seller : sellers) {
            SellerListDTO sellerForShow = new SellerListDTO(seller);
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

        if(passwordEncoder.matches(seller.getUser().getPassword(),
                userJpa.getPassword()) ||
                passwordEncoder.matches(passwordEncoder.encode(seller.getUser().getPassword()),
                        userJpa.getPassword())) {
            userJpa.setPassword(passwordEncoder.encode(seller.getUser().getPassword()));
        }

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


        return ok;
    }
}
