package com.project.pharmacy_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserGetResponseDto {

    private Long userId;

    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private RoleDto role;
}
