package com.project.pharmacy_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO {


    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;
}
