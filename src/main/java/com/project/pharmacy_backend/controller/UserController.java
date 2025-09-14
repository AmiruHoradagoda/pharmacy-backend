package com.project.pharmacy_backend.controller;

import com.project.pharmacy_backend.dto.response.pagination.UserPaginationResponseDto;
import com.project.pharmacy_backend.service.UserService;
import com.project.pharmacy_backend.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/user")

public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getCustomers")
    public ResponseEntity<StandardResponse> getCustomers(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @RequestParam(defaultValue = "userId") String sortBy,
                                                         @RequestParam(defaultValue = "desc") String sortDirection){
        UserPaginationResponseDto dto = userService.getAllCustomers(page, size, sortBy, sortDirection);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200, "Customers are retrieved", dto),
                HttpStatus.OK
        );
    }
}
