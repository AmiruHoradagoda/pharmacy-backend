package com.project.pharmacy_backend.dto.response.pagination;

import com.project.pharmacy_backend.dto.response.UserGetResponseDto;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPaginationResponseDto {
    private List<UserGetResponseDto> dataList;
    private long dataCount;
}
