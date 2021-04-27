package com.ftn.osa.model.dto;

import com.ftn.osa.model.entity.Customer;
import com.ftn.osa.model.entity.Role;
import com.ftn.osa.model.entity.Seller;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class SellerDTO {

    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String address;

    @NotBlank
    private String email;

    @NotBlank
    private String sellerName;


    public SellerDTO(Seller createdUser) {
        this.id = createdUser.getId();
        this.username = createdUser.getUser().getUsername();
        this.name = createdUser.getUser().getName();
        this.password = createdUser.getUser().getPassword();
        this.surname = createdUser.getUser().getSurname();
        this.address = createdUser.getAddress();
        this.email = createdUser.getEmail();
        this.sellerName = createdUser.getSellerName();
    }

}
