package com.ftn.osa.service.impl;

import com.ftn.osa.model.dto.CustomerDTO;
import com.ftn.osa.model.dto.SellerDTO;
import com.ftn.osa.model.entity.Customer;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.model.entity.User;
import com.ftn.osa.repository.CustomerRepository;
import com.ftn.osa.repository.UserRepository;
import com.ftn.osa.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public CustomerDTO getLoggedIn(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        System.out.println("TRENUTNI ULOGOVANI usernname =" + userPrincipal.getUsername());
        String username = userPrincipal.getUsername();
        Customer customer = customerRepository.findByUsername(username).get();
        CustomerDTO customerDTO = new CustomerDTO(customer);
        return customerDTO;
    }

    @Override
    public Customer update(Customer customer) {
        User userJpa = userRepository.findById(customer.getId()).get();

        userJpa.setName(customer.getUser().getName());
        userJpa.setSurname(customer.getUser().getSurname());
        userJpa.setUsername(customer.getUser().getUsername());
        userJpa.setPassword(passwordEncoder.encode(customer.getUser().getPassword()));
        userRepository.save(userJpa);

        Customer customerJpa = customerRepository.findById(customer.getId()).get();
        customerJpa.setAddress(customer.getAddress());

        return customerRepository.save(customerJpa);
    }
}
