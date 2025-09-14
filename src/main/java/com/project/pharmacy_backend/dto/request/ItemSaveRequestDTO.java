package com.project.pharmacy_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemSaveRequestDTO {
    private String itemName;
    private int stockQuantity;
    private double itemPrice;
    private boolean activeState;
    private String imageUrl;

}
