package com.project.pharmacy_backend.service;

import com.project.pharmacy_backend.dto.request.RequestOrderSaveDTO;
import com.project.pharmacy_backend.dto.response.OrderGetResponseDto;
import com.project.pharmacy_backend.dto.response.pagination.OrderPaginateResponseDto;
import com.project.pharmacy_backend.util.enums.OrderStatus;


public interface OrderService {
    String addOrder(RequestOrderSaveDTO requestOrderSaveDTO);

    OrderPaginateResponseDto getAllOrders(int page, int size, String sortBy, String sortDirection);

    String changeOrderStatus(Long orderId, OrderStatus status);

    OrderGetResponseDto viewOrderById(long orderId);
}
