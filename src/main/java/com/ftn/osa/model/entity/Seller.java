package com.ftn.osa.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "seller")
public class Seller{

    @Column(nullable = false)
    private Date since;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String sellerName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;

    @Id
    private Long id;

    public Seller(Date since, String email, String address,
                  String sellerName, User user, Long id) {
        this.since = since;
        this.email = email;
        this.address = address;
        this.sellerName = sellerName;
        this.user = user;
        this.id = id;
    }

    public Seller(Date since, String email, String address,
                  String sellerName, User user) {
        this.since = since;
        this.email = email;
        this.address = address;
        this.sellerName = sellerName;
        this.user = user;
    }
}
