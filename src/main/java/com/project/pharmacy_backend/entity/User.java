package com.project.pharmacy_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "user_id", length = 45)
//    private Long userId;

    @Column(nullable = false, unique = true, name = "email")
    private String email;

    @Column(nullable = false, name = "first_Name")
    private String firstName;

    @Column(nullable = false, name = "last_Name")
    private String lastName;

    @Column(nullable = false, name = "phone_Number")
    private String phoneNumber;

    @Column(nullable = false, name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> role;

    @OneToMany(mappedBy = "customer")
    private Set<Order> orders;
}