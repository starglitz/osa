package com.ftn.osa.model.dto;

import com.ftn.osa.model.entity.Customer;
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

    private List<OrderItem> items = new ArrayList<>();

    //private List<Long> items = new ArrayList<>();

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", time=" + time +
                ", delivered=" + delivered +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", anonymous=" + anonymous +
                ", archived=" + archived +
                ", customer=" + customer +
                ", items=" + items +
                '}';
    }
}
