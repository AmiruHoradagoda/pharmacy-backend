package com.project.pharmacy_backend.service;

import com.project.pharmacy_backend.dto.UserDTO;
import com.project.pharmacy_backend.dto.response.UserGetResponseDto;
import com.project.pharmacy_backend.dto.response.pagination.UserPaginationResponseDto;

public interface UserService {
    String registerUser(UserDTO customerDTO);
    void initCustomerRoleAndCustomer();
    void initAdminRoleAndAdmin();

    UserPaginationResponseDto getAllUsers(int page, int size, String sortBy, String sortDirection);

    UserGetResponseDto getUserById(long userId);
}
