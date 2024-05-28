package com.project.pharmacy_backend.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
@TypeDefs({
        @TypeDef(name = "json",typeClass = JsonType.class)
})
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId", nullable = false)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "orderDate", nullable = false)
    private Date orderDate;

    @Column(name = "totalAmount", nullable = false)
    private double totalAmount;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private Set<OrderItems> orderItems;

     @OneToOne(cascade = CascadeType.ALL)
     @JoinColumn(name = "shipping_address_id", referencedColumnName = "shipping_id", nullable = false)
     private ShippingAddress shippingAddress;

    public Order(Customer customer, Date orderDate, double totalAmount,ShippingAddress shippingAddress) {
        this.customer = customer;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
    }
}
