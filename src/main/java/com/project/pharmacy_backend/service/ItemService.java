package com.project.pharmacy_backend.service;

import com.project.pharmacy_backend.dto.request.ItemSaveRequestDTO;
import com.project.pharmacy_backend.dto.response.ItemGetRequestDTO;

import java.util.List;

public interface ItemService {
    String saveItem(ItemSaveRequestDTO itemSaveRequestDTO);
    void initialItems();

    List<ItemGetRequestDTO> getAllItems();
}
