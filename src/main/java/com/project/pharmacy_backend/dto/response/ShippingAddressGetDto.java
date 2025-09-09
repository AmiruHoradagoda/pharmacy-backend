package com.project.pharmacy_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShippingAddressGetDto {

    private Long shipping_id;

    private String address;

    private String city;

    private String postalCode;
}
