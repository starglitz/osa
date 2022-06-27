package com.ftn.osa.service.impl;

import com.ftn.osa.OsaApplication;
import com.ftn.osa.model.entity.Customer;
import com.ftn.osa.model.entity.Role;
import com.ftn.osa.model.entity.User;
import com.ftn.osa.repository.CustomerRepository;
import com.ftn.osa.repository.UserRepository;
import com.ftn.osa.rest.dto.CustomerDTO;
import com.ftn.osa.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public CustomerDTO findById(Long id) {
        Optional<Customer> customer = this.findCustomerById(id);
        return customer.map(CustomerDTO::fromEntity).orElse(null);
    }

    @Override
    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public CustomerDTO getLoggedIn(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        String username = userPrincipal.getUsername();
        Customer customer = customerRepository.findByUsername(username).get();
        return CustomerDTO.fromEntity(customer);
    }

    @Override
    public boolean update(CustomerDTO customerDTO, String validatePassword) {

        User user = new User(customerDTO.getId(), customerDTO.getName(), customerDTO.getSurname(),
                customerDTO.getUsername(), customerDTO.getPassword(), true, Role.CUSTOMER);

        Customer customer = new Customer(customerDTO.getAddress(), user, user.getId());

        boolean ok = true;
        User userJpa = userRepository.findById(customer.getId()).get();

        userJpa.setName(customer.getUser().getName());
        userJpa.setSurname(customer.getUser().getSurname());
        userJpa.setUsername(customer.getUser().getUsername());

        if(passwordEncoder.matches(validatePassword,
                userJpa.getPassword())) {
            userJpa.setPassword(passwordEncoder.encode(customer.getUser().getPassword()));
            OsaApplication.log.info("Password successfully changed");
        }

        else {
            ok = false;
        }

        userRepository.save(userJpa);

        Customer customerJpa = customerRepository.findById(customer.getId()).get();
        customerJpa.setAddress(customer.getAddress());

        customerRepository.save(customerJpa);
        OsaApplication.log.info("User successfully updated");
        return ok;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {

        Optional<User> user = userRepository.findFirstByUsername(customerDTO.getUsername());

        if(user.isPresent()){
            return null;
        }

        User newUser = new User(customerDTO.getName(), customerDTO.getSurname(),
                customerDTO.getUsername(), passwordEncoder.encode(customerDTO.getPassword()),
                true, Role.CUSTOMER);

        Customer customer = new Customer(customerDTO.getAddress(), newUser);

        User userJpa = userRepository.save(customer.getUser());
        customer.setUser(userJpa);
        customer.setId(userJpa.getId());

        customer = customerRepository.save(customer);
        OsaApplication.log.info("Successfully registered a customer");
        return CustomerDTO.fromEntity(customer);
    }
}
