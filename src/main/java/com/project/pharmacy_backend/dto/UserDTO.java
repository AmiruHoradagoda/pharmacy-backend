package com.project.pharmacy_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Role;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public  class UserDTO {
    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String password;

    private String roleName;
}
