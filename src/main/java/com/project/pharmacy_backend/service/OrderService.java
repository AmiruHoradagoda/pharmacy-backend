package com.project.pharmacy_backend.service;

import com.project.pharmacy_backend.dto.request.RequestOrderSaveDTO;


public interface OrderService {
    String addOrder(RequestOrderSaveDTO requestOrderSaveDTO);
}
