package com.project.pharmacy_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MonthlySalesDto {
    private String month;
    private Integer monthNumber;
    private Double revenue;
    private Long orderCount;
}