package com.ftn.osa.rest;

import com.ftn.osa.model.dto.*;
import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public interface UserApi {

    @PostMapping("/register")
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO newUser);

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDto);

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value="/users", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getAll();

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/users/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody UserListDTO userDTO);

}
