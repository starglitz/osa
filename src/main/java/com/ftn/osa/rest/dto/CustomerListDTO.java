package com.ftn.osa.rest.dto;

import com.ftn.osa.model.entity.Customer;
import com.ftn.osa.model.entity.Seller;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerListDTO {

    private Long id;

    private String name;

    private String surname;

    private String address;

    public CustomerListDTO(Customer createdUser) {
        this.id = createdUser.getId();
        this.name = createdUser.getUser().getName();
        this.surname = createdUser.getUser().getSurname();
        this.address = createdUser.getAddress();
    }
}
