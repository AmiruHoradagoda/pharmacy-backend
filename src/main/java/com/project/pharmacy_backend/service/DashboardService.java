package com.project.pharmacy_backend.service;

import com.project.pharmacy_backend.dto.response.*;

import java.util.List;

public interface DashboardService {
    DashboardStatsResponse getDashboardStats();

    List<RecentOrderDto> getRecentOrders(int limit);

    List<LowStockItemDto> getLowStockItems(int threshold);

    SalesOverviewResponse getSalesOverview(int days);

    AnnualSalesResponse getAnnualSalesData(int year);
}
