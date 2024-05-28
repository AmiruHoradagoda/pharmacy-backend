package com.project.pharmacy_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemSaveRequestDTO {
    private Long itemId;
    private String itemName;
    private int stockQuantity;
    private double itemPrice;
    private String imageUrl;

}
