package com.ftn.osa.model.dto;

import com.ftn.osa.model.entity.Customer;
import com.ftn.osa.model.entity.Seller;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SellerListDTO {


    private Long id;

    private String sellerName;

    private String name;

    private String surname;

    private String address;

    private String email;

    public SellerListDTO(Seller createdUser) {
        this.id = createdUser.getId();
        //this.username = createdUser.getUser().getUsername();
        this.name = createdUser.getUser().getName();
        //this.password = createdUser.getUser().getPassword();
        this.surname = createdUser.getUser().getSurname();
        this.address = createdUser.getAddress();
        this.email = createdUser.getEmail();
        this.sellerName = createdUser.getSellerName();
    }

}
