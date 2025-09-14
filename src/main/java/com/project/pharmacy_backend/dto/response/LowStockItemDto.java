package com.project.pharmacy_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LowStockItemDto {
    private Long itemId;
    private String itemName;
    private Integer currentStock;
    private Integer minStockThreshold;
    private String imageUrl;
}