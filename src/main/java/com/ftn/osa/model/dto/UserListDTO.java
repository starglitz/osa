package com.ftn.osa.model.dto;

import com.ftn.osa.model.entity.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UserListDTO {

    @NotBlank
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private boolean blocked;

    @NotBlank
    private Role role;
}
