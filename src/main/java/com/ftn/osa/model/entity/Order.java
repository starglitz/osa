package com.ftn.osa.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Data
@NoArgsConstructor
@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="order_id", unique=true, nullable=false)
    private Long id;

    private Date time;

    private boolean delivered;

    private int rating;

    private String comment;

    private boolean anonymous;

    private boolean archived;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="user_id", nullable=false)
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="order")
    private List<OrderItem> items = new ArrayList<>();


    public Order(Long id, boolean delivered, int rating, String comment, boolean anonymous, boolean archived) {
        this.delivered = delivered;
        this.rating = rating;
        this.comment = comment;
        this.anonymous = anonymous;
        this.archived = archived;
        this.id = id;
    }
}
