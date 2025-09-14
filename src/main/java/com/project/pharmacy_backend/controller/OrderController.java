package com.project.pharmacy_backend.controller;

import com.project.pharmacy_backend.dto.request.RequestOrderSaveDTO;
import com.project.pharmacy_backend.dto.response.OrderGetResponseDto;
import com.project.pharmacy_backend.dto.response.pagination.OrderPaginateResponseDto;
import com.project.pharmacy_backend.service.OrderService;
import com.project.pharmacy_backend.util.StandardResponse;
import com.project.pharmacy_backend.util.enums.OrderStatus;
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

    @GetMapping("/viewOrder/{id}")
    public ResponseEntity<StandardResponse> viewOrderById(@PathVariable("id") long orderId){
        OrderGetResponseDto dto = orderService.viewOrderById(orderId);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200, "Order details are retrieved", dto),
                HttpStatus.OK
        );
    }

    @PutMapping("changeOrderStatus/{id}")
    public ResponseEntity<StandardResponse> changeOrderStatus(@PathVariable("id") Long orderId,@RequestBody OrderStatus status){
        String message = orderService.changeOrderStatus(orderId,status);
        return new ResponseEntity<StandardResponse>(new StandardResponse(201,"Order status change to the "+status.toString(),message), HttpStatus.CREATED);
    }
}
