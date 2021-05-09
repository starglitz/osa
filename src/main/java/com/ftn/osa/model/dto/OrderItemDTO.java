package com.ftn.osa.model.dto;

import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
public class OrderItemDTO {
    private Long id;

    private int amount;

    private Order order;

    private Article article;
}
