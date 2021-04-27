package com.ftn.osa.service;

import com.ftn.osa.model.dto.UserDTO;
import com.ftn.osa.model.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User findByUsername(String username);

    User createUser(UserDTO userDTO);
}
