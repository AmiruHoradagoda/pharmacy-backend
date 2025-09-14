package com.project.pharmacy_backend.dto.response.pagination;

import com.project.pharmacy_backend.dto.response.ItemGetRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemPaginationResponseDto {
    private List<ItemGetRequestDTO> dataList;
    private long dataCount;
}
