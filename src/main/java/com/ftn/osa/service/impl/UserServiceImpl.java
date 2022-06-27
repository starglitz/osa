package com.ftn.osa.service.impl;

import com.ftn.osa.OsaApplication;
import com.ftn.osa.rest.dto.UserDTO;
import com.ftn.osa.rest.dto.UserListDTO;
import com.ftn.osa.model.entity.User;
import com.ftn.osa.repository.CustomerRepository;
import com.ftn.osa.repository.SellerRepository;
import com.ftn.osa.repository.UserRepository;
import com.ftn.osa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findFirstByUsername(username);
        return user.orElse(null);
    }

    @Override
    public User createUser(UserDTO userDTO) {

        Optional<User> user = userRepository.findFirstByUsername(userDTO.getUsername());

        if(user.isPresent()){
            OsaApplication.log.info("User that is tried to be added already exists");
            return null;

        }

        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser = userRepository.save(newUser);
        OsaApplication.log.info("User successfully created");
        return newUser;
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAllNoAdmin();
    }

    @Override
    public User update(UserListDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).get();
        user.setUsername(userDTO.getUsername());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEnabled(userDTO.isEnabled());
        OsaApplication.log.info("User successfully updated");
        return userRepository.save(user);
    }
}
