package com.project.pharmacy_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AnnualSalesResponse {
    private List<MonthlySalesDto> monthlySales;
    private Integer year;
    private Double totalAnnualRevenue;
    private Long totalAnnualOrders;
}