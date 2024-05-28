package com.project.pharmacy_backend.service.impl;

import com.project.pharmacy_backend.dto.request.ItemSaveRequestDTO;
import com.project.pharmacy_backend.entity.Item;
import com.project.pharmacy_backend.repo.ItemRepo;
import com.project.pharmacy_backend.service.ItemService;
import com.project.pharmacy_backend.util.mappers.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceIMPL implements ItemService {
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private ItemMapper itemMapper;
    @Override
    public String saveItem(ItemSaveRequestDTO itemSaveRequestDTO) {
        Item item = itemMapper.itemDtoToItemEntity(itemSaveRequestDTO);
        itemRepo.save(item);
        return itemSaveRequestDTO.getItemName() + "Insert Successfully";
    }
}
