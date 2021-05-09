package com.ftn.osa.service;

import com.ftn.osa.model.dto.CustomerDTO;
import com.ftn.osa.model.dto.SellerDTO;
import com.ftn.osa.model.dto.UserDTO;
import com.ftn.osa.model.entity.Customer;
import com.ftn.osa.model.entity.Seller;
import com.ftn.osa.model.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User findByUsername(String username);

    User createUser(UserDTO userDTO);

    Customer createCustomer(CustomerDTO customerDTO);

    Seller createSeller(SellerDTO sellerDTO);


}
