package com.project.pharmacy_backend.dto.response;

import com.project.pharmacy_backend.util.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderGetResponseDto {

    private Long orderId;

    private UserGetResponseDto customer;

    private Date orderDate;

    private OrderStatus status;

    private double totalAmount;

    private List<OrderItemGetDto> orderItems;

    private ShippingAddressGetDto shippingAddress;
}
