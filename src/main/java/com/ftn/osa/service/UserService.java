package com.ftn.osa.service;

import com.ftn.osa.rest.dto.UserDTO;
import com.ftn.osa.rest.dto.UserListDTO;
import com.ftn.osa.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User findByUsername(String username);

    User createUser(UserDTO userDTO);

    List<User> findAll();

    User update(UserListDTO userDTO);

}
