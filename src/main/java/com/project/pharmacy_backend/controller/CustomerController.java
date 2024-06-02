package com.project.pharmacy_backend.controller;

import com.project.pharmacy_backend.dto.UserDTO;
import com.project.pharmacy_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("api/v1/customer")
@CrossOrigin
public class CustomerController extends UserController<UserDTO>{
    @Autowired
    public UserService customerService;

    @PostConstruct
    public void initCustomerRoleAndCustomer(){
        customerService.initCustomerRoleAndCustomer();
    }

    @Override
    protected String saveEntity(UserDTO customerDTO) {
        return customerService.saveUser(customerDTO);
    }
}
