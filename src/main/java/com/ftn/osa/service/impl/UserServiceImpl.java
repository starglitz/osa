package com.ftn.osa.service.impl;

import com.ftn.osa.model.dto.CustomerDTO;
import com.ftn.osa.model.dto.SellerDTO;
import com.ftn.osa.model.dto.UserDTO;
import com.ftn.osa.model.dto.UserListDTO;
import com.ftn.osa.model.entity.Customer;
import com.ftn.osa.model.entity.Role;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.model.entity.User;
import com.ftn.osa.repository.AdminRepository;
import com.ftn.osa.repository.CustomerRepository;
import com.ftn.osa.repository.SellerRepository;
import com.ftn.osa.repository.UserRepository;
import com.ftn.osa.service.UserService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
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

    /*
    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }
*/
    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findFirstByUsername(username);
        if (!user.isEmpty()) {
            return user.get();
        }
        return null;
    }

    @Override
    public User createUser(UserDTO userDTO) {

        Optional<User> user = userRepository.findFirstByUsername(userDTO.getUsername());

        if(user.isPresent()){
            return null;
        }

        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser = userRepository.save(newUser);

        return newUser;
    }

    @Override
    public Customer createCustomer(CustomerDTO customerDTO) {

        Optional<User> user = userRepository.findFirstByUsername(customerDTO.getUsername());

        if(user.isPresent()){
            return null;
        }

        User newUser = new User();
        Customer newCustomer = new Customer();

        newUser.setUsername(customerDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        newUser.setName(customerDTO.getName());
        newUser.setSurname(customerDTO.getSurname());
        newUser.setBlocked(false);
        newUser.setRole(Role.CUSTOMER);

        newUser = userRepository.save(newUser);

        newCustomer.setUser(newUser);
        newCustomer.setId(newUser.getId());
        newCustomer.setAddress(customerDTO.getAddress());

        newCustomer = customerRepository.save(newCustomer);
        return newCustomer;
    }

    @Override
    public Seller createSeller(SellerDTO sellerDTO) {
        Optional<User> user = userRepository.findFirstByUsername(sellerDTO.getUsername());

        if(user.isPresent()){
            return null;
        }

        User newUser = new User();
        Seller newSeller = new Seller();

        newUser.setUsername(sellerDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(sellerDTO.getPassword()));
        newUser.setName(sellerDTO.getName());
        newUser.setSurname(sellerDTO.getSurname());
        newUser.setBlocked(false);
        newUser.setRole(Role.SELLER);

        newUser = userRepository.save(newUser);

        newSeller.setUser(newUser);
        newSeller.setId(newUser.getId());
        newSeller.setAddress(sellerDTO.getAddress());
        newSeller.setEmail(sellerDTO.getEmail());
        newSeller.setSellerName(sellerDTO.getSellerName());
        newSeller.setSince(new Date());
        newSeller = sellerRepository.save(newSeller);
        return newSeller;
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
        user.setBlocked(userDTO.isBlocked());

        return userRepository.save(user);
    }
}
