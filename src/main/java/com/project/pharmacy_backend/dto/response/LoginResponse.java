package com.project.pharmacy_backend.dto.response;

import com.project.pharmacy_backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponse {
    private User user;
    private String jwtToken;

}