package com.project.pharmacy_backend.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Entity
@TypeDefs({
        @TypeDef(name = "json",typeClass = JsonType.class)
})
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipping_id;

    @Column(name = "Address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(nullable = false)
    private String postalCode;

    @OneToOne(mappedBy = "shippingAddress")
    private Order order;
}
