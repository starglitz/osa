package com.ftn.osa.model.dto;

import com.ftn.osa.model.entity.Customer;
import com.ftn.osa.model.entity.Role;
import com.ftn.osa.model.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CustomerDTO {

    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private Role role;

    @NotBlank
    private String surname;

    @NotBlank
    private boolean blocked;


    public CustomerDTO(Customer createdUser) {
        this.id = createdUser.getId();
        this.username = createdUser.getUser().getUsername();
        this.role = createdUser.getUser().getRole();
        this.surname = createdUser.getUser().getSurname();
        this.blocked = createdUser.getUser().isBlocked();
    }
}
