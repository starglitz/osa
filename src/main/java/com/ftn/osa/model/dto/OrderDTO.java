package com.ftn.osa.model.dto;

import com.ftn.osa.model.entity.Customer;
import com.ftn.osa.model.entity.Order;
import com.ftn.osa.model.entity.OrderItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
@Data
@NoArgsConstructor
public class OrderDTO {


    private Long id;

    private Date time;

    private boolean delivered;

    private int rating;

    private String comment;

    private boolean anonymous;

    private boolean archived;

    private Customer customer;

    private List<OrderItemDTO> items = new ArrayList<>();

    //private List<Long> items = new ArrayList<>();

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.time = order.getTime();
        this.delivered = order.isDelivered();
        this.rating = order.getRating();
        this.comment = order.getComment();
        this.anonymous = order.isAnonymous();
        this.archived = order.isArchived();
        this.customer = order.getCustomer();
        List<OrderItemDTO> itemDTOS = new ArrayList<>();

        for(OrderItem item : order.getItems()) {
            OrderItemDTO itemDTO = new OrderItemDTO(item);
            itemDTOS.add(itemDTO);
        }
        this.items = itemDTOS;
    }
}
