package com.ftn.osa.rest.impl;

import com.ftn.osa.model.dto.CustomerDTO;
import com.ftn.osa.model.dto.SellerDTO;
import com.ftn.osa.model.dto.UserDTO;
import com.ftn.osa.model.dto.UserListDTO;
import com.ftn.osa.model.entity.Customer;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.model.entity.User;
import com.ftn.osa.rest.UserApi;
import com.ftn.osa.security.TokenUtils;
import com.ftn.osa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Component
public class UserApiImpl implements UserApi {

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenUtils tokenUtils;

    /*
    @Autowired
    public UserController(UserServiceImpl userService, AuthenticationManager authenticationManager,
                          UserDetailsService userDetailsService, TokenUtils tokenUtils){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenUtils = tokenUtils;
    }
    */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> create(@RequestBody @Validated UserDTO newUser){

        User createdUser = userService.createUser(newUser);

        if(createdUser == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        UserDTO userDTO = new UserDTO(createdUser);

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
            return ResponseEntity.ok(tokenUtils.generateToken(userDetails));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/registerCustomer")
    public ResponseEntity<CustomerDTO> create(@RequestBody @Validated CustomerDTO newUser){

        Customer createdCustomer = userService.createCustomer(newUser);
        //User createdUser = userService.createUser(newUser);

        if(createdCustomer == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        CustomerDTO customerDTO = new CustomerDTO(createdCustomer);
        //UserDTO userDTO = new UserDTO(createdUser);

        return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<SellerDTO> create(@RequestBody @Validated SellerDTO newUser) {
        Seller createdSeller = userService.createSeller(newUser);
        //User createdUser = userService.createUser(newUser);

        if(createdSeller == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        SellerDTO sellerDTO = new SellerDTO(createdSeller);
        //UserDTO userDTO = new UserDTO(createdUser);

        return new ResponseEntity<>(sellerDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity getAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity update(Long id, @Valid UserListDTO userDTO) {
        return new ResponseEntity<>(userService.update(userDTO), HttpStatus.OK);
    }
}
