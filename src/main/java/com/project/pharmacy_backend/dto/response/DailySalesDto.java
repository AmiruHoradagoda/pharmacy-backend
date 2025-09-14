package com.project.pharmacy_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DailySalesDto {
    private LocalDate date;
    private Double sales;
    private Integer orderCount;
}