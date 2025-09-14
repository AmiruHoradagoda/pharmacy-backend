package com.project.pharmacy_backend.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SalesOverviewResponse {
    private List<DailySalesDto> dailySales;
    private Double totalSales;
    private Double averageDailySales;
}

