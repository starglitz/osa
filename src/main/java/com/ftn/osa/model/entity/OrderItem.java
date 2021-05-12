package com.ftn.osa.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id",referencedColumnName="order_id", nullable=false)
    private Order order;

    @ManyToOne
    @JoinColumn(name="article_id", referencedColumnName="article_id", nullable=false)
    private Article article;

}
