package com.project.pharmacy_backend.controller;

import com.project.pharmacy_backend.dto.request.RequestOrderSaveDTO;
import com.project.pharmacy_backend.service.OrderService;
import com.project.pharmacy_backend.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/save")
    public ResponseEntity<StandardResponse> saveOrder(@RequestBody RequestOrderSaveDTO requestOrderSaveDTO){
        String message = orderService.addOrder(requestOrderSaveDTO);
        return new ResponseEntity<StandardResponse>(new StandardResponse(201,"Order is Saved",message), HttpStatus.CREATED);
    }
}
