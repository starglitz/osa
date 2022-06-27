package com.ftn.osa.rest.dto;

import com.ftn.osa.model.entity.Role;
import com.ftn.osa.model.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;


    public UserDTO(User createdUser) {
      //  this.id = createdUser.getId();
        this.username = createdUser.getUsername();
        this.password = createdUser.getPassword();
    }
}
