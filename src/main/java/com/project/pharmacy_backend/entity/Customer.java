package com.project.pharmacy_backend.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "customers")
@TypeDefs({
        @TypeDef(name = "json",typeClass = JsonType.class)
})
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", length = 45)
    private Long customerId;

    @Column(nullable = false, name = "email")
    private String email;

    @Column(nullable = false, name = "first_Name")
    private String firstName;

    @Column(nullable = false, name = "last_Name")
    private String lastName;

    @Column(nullable = false, name = "phone_Number")
    private String phoneNumber;

    @OneToMany(mappedBy = "customer")
    private Set<Order> orders;
}
