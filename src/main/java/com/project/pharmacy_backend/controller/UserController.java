package com.project.pharmacy_backend.controller;

import com.project.pharmacy_backend.dto.response.UserGetResponseDto;
import com.project.pharmacy_backend.dto.response.pagination.UserPaginationResponseDto;
import com.project.pharmacy_backend.service.UserService;
import com.project.pharmacy_backend.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/user")

public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<StandardResponse> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @RequestParam(defaultValue = "userId") String sortBy,
                                                         @RequestParam(defaultValue = "desc") String sortDirection){
        UserPaginationResponseDto dto = userService.getAllUsers(page, size, sortBy, sortDirection);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200, "Customers are retrieved", dto),
                HttpStatus.OK
        );
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<StandardResponse> getUserById(@PathVariable("id") long userId){
        UserGetResponseDto dto = userService.getUserById(userId);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200, "User data retrieved", dto),
                HttpStatus.OK
        );
    }
}
