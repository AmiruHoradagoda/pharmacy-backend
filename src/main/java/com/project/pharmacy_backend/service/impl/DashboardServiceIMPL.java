package com.project.pharmacy_backend.service.impl;

import com.project.pharmacy_backend.dto.response.*;
import com.project.pharmacy_backend.entity.Item;
import com.project.pharmacy_backend.entity.Order;
import com.project.pharmacy_backend.repo.*;
import com.project.pharmacy_backend.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardServiceIMPL implements DashboardService {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private UserRepo customerRepo;

    @Override
    public DashboardStatsResponse getDashboardStats() {
        // Get current stats
        Long totalOrders = orderRepo.count();
        Double totalRevenue = orderRepo.sumTotalAmount();
        if (totalRevenue == null) totalRevenue = 0.0;

        Long totalProducts = itemRepo.countByActiveStateTrue();
        Long lowStockCount = itemRepo.countByStockQuantityLessThanEqual(5);

        // Get previous month stats for growth calculation
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        Date oneMonthAgoDate = Date.from(oneMonthAgo.atZone(ZoneId.systemDefault()).toInstant());

        Long previousOrders = orderRepo.countByOrderDateBefore(oneMonthAgoDate);
        Double previousRevenue = orderRepo.sumTotalAmountBefore(oneMonthAgoDate);
        if (previousRevenue == null) previousRevenue = 0.0;

        // Calculate growth percentages
        Double orderGrowthPercentage = calculateGrowthPercentage(previousOrders, totalOrders);
        Double revenueGrowthPercentage = calculateGrowthPercentage(previousRevenue, totalRevenue);

        return DashboardStatsResponse.builder()
                .totalOrders(totalOrders)
                .totalRevenue(totalRevenue)
                .totalProducts(totalProducts)
                .lowStockCount(lowStockCount)
                .orderGrowthPercentage(orderGrowthPercentage)
                .revenueGrowthPercentage(revenueGrowthPercentage)
                .productGrowthPercentage(0.0) // Can be calculated if you track product creation dates
                .lowStockChangePercentage(-5.0) // Placeholder - can be calculated with historical data
                .build();
    }

    @Override
    public List<RecentOrderDto> getRecentOrders(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "orderDate"));
        List<Order> orders = orderRepo.findAll(pageable).getContent();

        return orders.stream()
                .map(this::mapToRecentOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LowStockItemDto> getLowStockItems(int threshold) {
        List<Item> lowStockItems = itemRepo.findByStockQuantityLessThanEqualAndActiveStateTrue(threshold);

        return lowStockItems.stream()
                .map(this::mapToLowStockDto)
                .collect(Collectors.toList());
    }

    @Override
    public SalesOverviewResponse getSalesOverview(int days) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        Date startDateAsDate = Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());

        // Get orders from the last X days
        List<Order> recentOrders = orderRepo.findByOrderDateAfter(startDateAsDate);

        Double totalSales = recentOrders.stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();

        Double averageDailySales = totalSales / days;

        // For now, return aggregated data. You can implement daily breakdown later
        return SalesOverviewResponse.builder()
                .totalSales(totalSales)
                .averageDailySales(averageDailySales)
                .dailySales(List.of()) // Empty for now, can be implemented later
                .build();
    }

    // Helper methods
    private Double calculateGrowthPercentage(Number previous, Number current) {
        if (previous == null || previous.doubleValue() == 0) {
            return current.doubleValue() > 0 ? 100.0 : 0.0;
        }
        return ((current.doubleValue() - previous.doubleValue()) / previous.doubleValue()) * 100;
    }

    private RecentOrderDto mapToRecentOrderDto(Order order) {
        return RecentOrderDto.builder()
                .orderId(order.getOrderId())
                .customerName(order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .build();
    }

    private LowStockItemDto mapToLowStockDto(Item item) {
        return LowStockItemDto.builder()
                .itemId(item.getItemId())
                .itemName(item.getItemName())
                .currentStock(item.getStockQuantity())
                .minStockThreshold(5) // You can make this configurable
                .imageUrl(item.getImageUrl())
                .build();
    }

    @Override
    public AnnualSalesResponse getAnnualSalesData(int year) {
        // Create date range for the year
        LocalDateTime startOfYear = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime endOfYear = LocalDateTime.of(year, 12, 31, 23, 59, 59);

        Date startDate = Date.from(startOfYear.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endOfYear.atZone(ZoneId.systemDefault()).toInstant());

        // Get all orders for the year
        List<Order> yearOrders = orderRepo.findByOrderDateBetween(startDate, endDate);

        // Group by month and calculate totals
        Map<Integer, List<Order>> ordersByMonth = yearOrders.stream()
                .collect(Collectors.groupingBy(order -> {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(order.getOrderDate());
                    return cal.get(Calendar.MONTH) + 1; // Convert to 1-12
                }));

        // Create monthly sales data
        List<MonthlySalesDto> monthlySales = new ArrayList<>();
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        for (int month = 1; month <= 12; month++) {
            List<Order> monthOrders = ordersByMonth.getOrDefault(month, new ArrayList<>());

            Double monthRevenue = monthOrders.stream()
                    .mapToDouble(Order::getTotalAmount)
                    .sum();

            Long monthOrderCount = (long) monthOrders.size();

            MonthlySalesDto monthlyData = MonthlySalesDto.builder()
                    .month(monthNames[month - 1])
                    .monthNumber(month)
                    .revenue(monthRevenue)
                    .orderCount(monthOrderCount)
                    .build();

            monthlySales.add(monthlyData);
        }

        // Calculate yearly totals
        Double totalAnnualRevenue = monthlySales.stream()
                .mapToDouble(MonthlySalesDto::getRevenue)
                .sum();

        Long totalAnnualOrders = monthlySales.stream()
                .mapToLong(MonthlySalesDto::getOrderCount)
                .sum();

        return AnnualSalesResponse.builder()
                .monthlySales(monthlySales)
                .year(year)
                .totalAnnualRevenue(totalAnnualRevenue)
                .totalAnnualOrders(totalAnnualOrders)
                .build();
    }



}