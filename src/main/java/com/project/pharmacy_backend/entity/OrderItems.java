package com.project.pharmacy_backend.entity;

import javax.persistence.*;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Entity
@TypeDefs({
        @TypeDef(name = "json",typeClass = JsonType.class)
})
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItemsID",nullable = false)
    private Long orderItemsID;

    @Column(name = "amount",nullable = false)
    private double amount;

    @Column(name = "itemName",nullable = false)
    private String itemName;

    @Column(name = "quantity",nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name="order_id",nullable = false)
    private Order orders;

    @ManyToOne
    @JoinColumn(name = "item_id",nullable = false)
    private Item items;
   


}
