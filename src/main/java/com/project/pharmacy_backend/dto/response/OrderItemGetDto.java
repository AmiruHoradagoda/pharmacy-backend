package com.project.pharmacy_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderItemGetDto {

    private Long orderItemsID;

    private double amount;

    private String itemName;

    private int quantity;

    private Long itemId;

}
