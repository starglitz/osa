package com.ftn.osa.rest;

import com.ftn.osa.model.dto.CustomerDTO;
import com.ftn.osa.model.dto.SellerDTO;
import com.ftn.osa.model.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public interface UserApi {

    @PostMapping("/register")
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO newUser);

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDto);

    @PostMapping("/registerCustomer")
    public ResponseEntity<CustomerDTO> create(@RequestBody @Valid CustomerDTO newUser);

    @PostMapping("/registerSeller")
    public ResponseEntity<SellerDTO> create(@RequestBody @Valid SellerDTO newUser);

}
