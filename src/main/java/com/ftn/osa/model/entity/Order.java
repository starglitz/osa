package com.ftn.osa.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

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
    @JoinColumn(name="customer_id", referencedColumnName="id", nullable=false)
    private Customer customer;

    @OneToMany(cascade={ALL}, fetch=EAGER, mappedBy="order")
    private Set<OrderItem> items = new HashSet<OrderItem>();


}
