package com.project.pharmacy_backend.controller;

import com.project.pharmacy_backend.dto.CustomerDTO;
import com.project.pharmacy_backend.service.CustomerService;
import com.project.pharmacy_backend.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customer")
@CrossOrigin
public class CustomerController {
    @Autowired
    public CustomerService customerService;

    @PostMapping(path = "/save")
    public ResponseEntity<StandardResponse> saveCustomer(@RequestBody CustomerDTO customerDTO){
        String message = customerService.saveCustomer(customerDTO);
        return new ResponseEntity<StandardResponse>(new StandardResponse(201,"Customer Saved",message), HttpStatus.CREATED);
    }
}
