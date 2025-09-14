package com.project.pharmacy_backend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    @Column(nullable = false, unique = true)
    private String roleName;

    @Column(nullable = false, unique = true)
    private String roleDescription;

}
