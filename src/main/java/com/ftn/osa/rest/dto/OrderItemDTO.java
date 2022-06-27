package com.ftn.osa.rest.dto;

import com.ftn.osa.model.entity.Article;
import com.ftn.osa.model.entity.Order;
import com.ftn.osa.model.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long id;

    private int amount;

    private Article article;

    public OrderItemDTO(OrderItem item) {
        this.id = item.getId();
        this.amount = item.getAmount();
        this.article = item.getArticle();
    }


    public static OrderItemDTO fromEntity(OrderItem item) {
        return new OrderItemDTO(item.getId(), item.getAmount(), item.getArticle());
    }

    public static List<OrderItemDTO> fromEntityList(List<OrderItem> items) {
        return items.stream().map(OrderItemDTO::fromEntity).collect(Collectors.toList());
    }
}
