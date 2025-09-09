package com.project.pharmacy_backend.controller;

import com.project.pharmacy_backend.dto.UserDTO;
import com.project.pharmacy_backend.dto.request.LoginRequest;
import com.project.pharmacy_backend.dto.response.LoginResponse;
import com.project.pharmacy_backend.service.JwtService;
import com.project.pharmacy_backend.service.UserService;
import com.project.pharmacy_backend.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    public UserService userService;

    @PostConstruct
    public void initCustomerRoleAndCustomer(){
        userService.initCustomerRoleAndCustomer();
    }
    @PostConstruct
    public void initAdminRoleAndAdmin(){
        userService.initAdminRoleAndAdmin();
    }

    //authentication === logging
    @PostMapping({"/authentication"})
    public LoginResponse createJwtTokenAndLogin(@RequestBody LoginRequest loginRequest) throws Exception{
        System.out.println(loginRequest);
        LoginResponse loginResponseReturn= jwtService.createJwtToken(loginRequest);

        return loginResponseReturn;
    }

    @PostMapping("/register")
    protected ResponseEntity<StandardResponse> registerUser(@RequestBody UserDTO userDTO) {
        System.out.println("=== REGISTER ENDPOINT DEBUG ===");
        System.out.println("Received request body: " + userDTO);
        System.out.println("===============================");
        String message = userService.registerUser(userDTO);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Registered Successfully", message), HttpStatus.OK);

    }

}
