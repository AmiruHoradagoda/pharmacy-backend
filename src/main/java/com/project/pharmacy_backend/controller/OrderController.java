package com.project.pharmacy_backend.controller;

import com.project.pharmacy_backend.dto.request.RequestOrderSaveDTO;
import com.project.pharmacy_backend.dto.response.pagination.OrderPaginateResponseDto;
import com.project.pharmacy_backend.service.OrderService;
import com.project.pharmacy_backend.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getAllOrders")
    public ResponseEntity<StandardResponse> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @RequestParam(defaultValue = "orderId") String sortBy,
                                                         @RequestParam(defaultValue = "desc") String sortDirection){
        OrderPaginateResponseDto dto = orderService.getAllOrders(page, size, sortBy, sortDirection);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200, "Orders are retrieved", dto),
                HttpStatus.OK
        );
    }

}
