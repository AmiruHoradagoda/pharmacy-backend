package com.project.pharmacy_backend.controller;

import com.project.pharmacy_backend.dto.response.*;
import com.project.pharmacy_backend.service.DashboardService;
import com.project.pharmacy_backend.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/dashboard")
@CrossOrigin
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<StandardResponse> getDashboardStats() {
        try {
            DashboardStatsResponse stats = dashboardService.getDashboardStats();
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Dashboard stats retrieved successfully", stats),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(500, "Error retrieving dashboard stats: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/recent-orders")
    public ResponseEntity<StandardResponse> getRecentOrders(
            @RequestParam(defaultValue = "10") int limit) {
        try {
            List<RecentOrderDto> recentOrders = dashboardService.getRecentOrders(limit);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Recent orders retrieved successfully", recentOrders),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(500, "Error retrieving recent orders: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/low-stock")
    public ResponseEntity<StandardResponse> getLowStockItems(
            @RequestParam(defaultValue = "5") int threshold) {
        try {
            List<LowStockItemDto> lowStockItems = dashboardService.getLowStockItems(threshold);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Low stock items retrieved successfully", lowStockItems),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(500, "Error retrieving low stock items: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/sales-overview")
    public ResponseEntity<StandardResponse> getSalesOverview(
            @RequestParam(defaultValue = "30") int days) {
        try {
            SalesOverviewResponse salesOverview = dashboardService.getSalesOverview(days);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Sales overview retrieved successfully", salesOverview),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(500, "Error retrieving sales overview: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
    @GetMapping("/annual-sales")
    public ResponseEntity<StandardResponse> getAnnualSalesData(
            @RequestParam(defaultValue = "2024") int year) {
        try {
            AnnualSalesResponse salesData = dashboardService.getAnnualSalesData(year);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Annual sales data retrieved successfully", salesData),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(500, "Error retrieving annual sales data: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}