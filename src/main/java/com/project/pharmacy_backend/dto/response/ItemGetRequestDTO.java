package com.project.pharmacy_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemGetRequestDTO {
    private Long itemId;
    private String itemName;
    private int stockQuantity;
    private boolean activeState;
    private double itemPrice;
    private String imageUrl;
}
