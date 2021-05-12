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

    private String passwordValidate = "";

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String address;


    public CustomerDTO(Customer createdUser) {
        this.id = createdUser.getId();
        this.username = createdUser.getUser().getUsername();
        this.name = createdUser.getUser().getName();
        this.password = createdUser.getUser().getPassword();
        this.surname = createdUser.getUser().getSurname();
        this.address = createdUser.getAddress();
    }
}
