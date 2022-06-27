package com.ftn.osa.rest.dto;

import com.ftn.osa.model.entity.Order;
import com.ftn.osa.model.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;

    private Date time;

    private boolean delivered;

    private int rating;

    private String comment;

    private boolean anonymous;

    private boolean archived;

    private CustomerListDTO customer;

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
        this.customer = new CustomerListDTO(order.getCustomer());
        List<OrderItemDTO> itemDTOS = new ArrayList<>();

        for(OrderItem item : order.getItems()) {
            OrderItemDTO itemDTO = new OrderItemDTO(item);
            itemDTOS.add(itemDTO);
        }
        this.items = itemDTOS;
    }

    public static OrderDTO fromEntity(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getTime(),
                order.isDelivered(),
                order.getRating(),
                order.getComment(),
                order.isAnonymous(),
                order.isArchived(),
                new CustomerListDTO(order.getCustomer()),
                OrderItemDTO.fromEntityList(order.getItems())
        );
    }

    public static List<OrderDTO> fromEntityList(List<Order> orders) {
        return orders.stream().map(OrderDTO::fromEntity).collect(Collectors.toList());
    }
}
