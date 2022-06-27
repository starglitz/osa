package com.ftn.osa.rest.impl;

import com.ftn.osa.rest.dto.UserDTO;
import com.ftn.osa.rest.dto.UserListDTO;
import com.ftn.osa.model.entity.User;
import com.ftn.osa.rest.UserApi;
import com.ftn.osa.security.TokenUtils;
import com.ftn.osa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;


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
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException ex) {
                return ResponseEntity.status(404).build();
        } catch (LockedException ex) {
            // do something
        } catch (DisabledException ex) {
                return ResponseEntity.status(403).build();
            }

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());

            return ResponseEntity.ok(tokenUtils.generateToken(userDetails));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(404).build();
        }
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
