package com.project.pharmacy_backend.entity;

import java.util.Set;

import javax.persistence.*;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Entity
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonType.class)
})
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_Id", nullable = false)
    private Long itemId;

    @Column(name = "item_Name", nullable = false)
    private String itemName;

    @Column(name = "active_state", columnDefinition = "TINYINT default 0")
    private boolean activeState;

    @Column(name = "stock_Quantity", nullable = false)
    private int stockQuantity;

    @Column(name = "item_Price", nullable = false)
    private double itemPrice;

    @Column(name = "image_Url", nullable = false, length = 500)
    private String imageUrl;

    @OneToMany(mappedBy = "items")
    private Set<OrderItems> orderItems;


}
