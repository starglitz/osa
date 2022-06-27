package com.ftn.osa.rest.dto;

import com.ftn.osa.model.entity.Order;
import com.ftn.osa.model.entity.OrderItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderUpdateDTO {

    private Long id;

    @NotNull
    private Date time;

    private boolean delivered;

    @NotNull
    @Min(1)
    @Max(5)
    private int rating;

    @NotBlank
    @NotNull
    private String comment;

    private boolean anonymous;

    private boolean archived;

    private CustomerListDTO customer;

    private List<OrderItemDTO> items = new ArrayList<>();

    //private List<Long> items = new ArrayList<>();

    public OrderUpdateDTO(Order order) {
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
}
