package com.project.pharmacy_backend.service;

import com.project.pharmacy_backend.dto.UserDTO;

public interface UserService {
    String registerUser(UserDTO customerDTO);
    void initCustomerRoleAndCustomer();
    void initAdminRoleAndAdmin();
}
