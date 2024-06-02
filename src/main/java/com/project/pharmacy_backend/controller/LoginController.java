package com.project.pharmacy_backend.controller;

import com.project.pharmacy_backend.dto.request.LoginRequest;
import com.project.pharmacy_backend.dto.response.LoginResponse;
import com.project.pharmacy_backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/login")
public class LoginController {
    @Autowired
    private JwtService jwtService;

    //authentication === logging
    @PostMapping({"/authentication"})
    public LoginResponse createJwtTokenAndLogin(@RequestBody LoginRequest loginRequest) throws Exception{
        System.out.println(loginRequest);
        LoginResponse loginResponseReturn= jwtService.createJwtToken(loginRequest);

        return jwtService.createJwtToken(loginRequest);
    }
}
