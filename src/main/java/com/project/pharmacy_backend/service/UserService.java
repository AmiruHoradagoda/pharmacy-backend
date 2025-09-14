package com.project.pharmacy_backend.service;

import com.project.pharmacy_backend.dto.UserDTO;
import com.project.pharmacy_backend.dto.response.pagination.UserPaginationResponseDto;

public interface UserService {
    String registerUser(UserDTO customerDTO);
    void initCustomerRoleAndCustomer();
    void initAdminRoleAndAdmin();

    UserPaginationResponseDto getAllCustomers(int page, int size, String sortBy, String sortDirection);
}
