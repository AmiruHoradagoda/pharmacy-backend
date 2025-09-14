package com.project.pharmacy_backend.service;

import com.project.pharmacy_backend.dto.request.ItemSaveRequestDTO;
import com.project.pharmacy_backend.dto.response.ItemGetRequestDTO;
import com.project.pharmacy_backend.dto.response.pagination.ItemPaginationResponseDto;

import java.util.List;

public interface ItemService {
    String saveItem(ItemSaveRequestDTO itemSaveRequestDTO);
    void initialItems();

    ItemPaginationResponseDto getAllItems(int page, int size, String sortBy, String sortDirection);

    ItemGetRequestDTO getItemById(long itemId);

    String deleteItemById(long itemId);


    String editItemById(long itemId, ItemSaveRequestDTO itemSaveRequestDTO);
}
