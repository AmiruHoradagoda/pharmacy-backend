package com.project.pharmacy_backend.dto.response.pagination;

import com.project.pharmacy_backend.dto.response.OrderGetResponseDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPaginateResponseDto {
    private List<OrderGetResponseDto> dataList;
    private long dataCount;
}