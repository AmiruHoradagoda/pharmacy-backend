package com.project.pharmacy_backend.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestShippingAddressSave {

    private String city;
    private String street;
    private String postalCode;
}
