package com.project.pharmacy_backend.service;

import com.project.pharmacy_backend.dto.request.ItemSaveRequestDTO;

public interface ItemService {
    String saveItem(ItemSaveRequestDTO itemSaveRequestDTO);
}
