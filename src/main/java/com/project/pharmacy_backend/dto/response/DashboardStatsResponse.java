package com.project.pharmacy_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DashboardStatsResponse {
    private Long totalOrders;
    private Double totalRevenue;
    private Long totalProducts;
    private Long lowStockCount;
    private Double orderGrowthPercentage;
    private Double revenueGrowthPercentage;
    private Double productGrowthPercentage;
    private Double lowStockChangePercentage;
}