package com.project.pharmacy_backend.service;

import com.project.pharmacy_backend.dto.request.RequestOrderSaveDTO;
import com.project.pharmacy_backend.dto.response.pagination.OrderPaginateResponseDto;


public interface OrderService {
    String addOrder(RequestOrderSaveDTO requestOrderSaveDTO);

    OrderPaginateResponseDto getAllOrders();
}
