package com.ftn.osa.model.es;

import com.ftn.osa.model.entity.Customer;
import com.ftn.osa.model.entity.Order;
import com.ftn.osa.model.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "order")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class OrderES {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private Date time;

    @Field(type = FieldType.Boolean)
    private boolean delivered;

    @Field(type = FieldType.Integer)
    private int rating;

    @Field(type = FieldType.Text)
    private String comment;

    @Field(type = FieldType.Boolean)
    private boolean anonymous;

    @Field(type = FieldType.Boolean)
    private boolean archived;

    @Field(type = FieldType.Nested, includeInParent = true)
    private Customer customer;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<OrderItem> items = new ArrayList<>();

    public static OrderES fromOrder(Order order) {
        return new OrderES(order.getId(), order.getTime(), order.isDelivered(),
                order.getRating(), order.getComment(), order.isAnonymous(), order.isArchived(),
                order.getCustomer(), order.getItems());
    }

    public static List<OrderES> fromOrderList(List<Order> orders) {
        List<OrderES> es = new ArrayList<>();
        for(Order order : orders) {
            es.add(fromOrder(order));
        }
        return es;
    }
}
