package com.project.pharmacy_backend.dto.response;

import com.project.pharmacy_backend.util.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RecentOrderDto {
    private Long orderId;
    private String customerName;
    private Date orderDate;
    private Double totalAmount;
    private OrderStatus status;
}