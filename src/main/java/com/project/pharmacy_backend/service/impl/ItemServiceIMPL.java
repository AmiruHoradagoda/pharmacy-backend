package com.project.pharmacy_backend.service.impl;

import com.project.pharmacy_backend.dto.request.ItemSaveRequestDTO;
import com.project.pharmacy_backend.dto.response.ItemGetRequestDTO;
import com.project.pharmacy_backend.entity.Item;
import com.project.pharmacy_backend.repo.ItemRepo;
import com.project.pharmacy_backend.service.ItemService;
import com.project.pharmacy_backend.util.mappers.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceIMPL implements ItemService {
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private ItemMapper itemMapper;

    public void initialItems() {
        addItemIfNotExists("Panadole", 100, 50, "https://via.placeholder.com/150");
        addItemIfNotExists("Aspirin", 150, 30, "https://via.placeholder.com/150");
        addItemIfNotExists("Tylenol", 200, 45, "https://via.placeholder.com/150");
        addItemIfNotExists("Ibuprofen", 180, 40, "https://via.placeholder.com/150");
        addItemIfNotExists("Amoxicillin", 120, 100, "https://via.placeholder.com/150");
    }
    private void addItemIfNotExists(String itemName, int stockQuantity, double itemPrice, String imageUrl) {
        if (!itemRepo.existsByItemName(itemName)) {
            Item item = new Item();
            item.setItemName(itemName);
            item.setStockQuantity(stockQuantity);
            item.setItemPrice(itemPrice);
            item.setImageUrl(imageUrl);
            item.setActiveState(true);
            itemRepo.save(item);
        }
    }

    @Override
    public String saveItem(ItemSaveRequestDTO itemSaveRequestDTO) {
        Item item = itemMapper.itemDtoToItemEntity(itemSaveRequestDTO);
        itemRepo.save(item);
        return itemSaveRequestDTO.getItemName() + "Insert Successfully";
    }



    @Override
    public List<ItemGetRequestDTO> getAllItems() {
        List<Item>itemList = itemRepo.findAll();
        List<ItemGetRequestDTO>itemListDto =itemMapper.itemListToItemListDto(itemList);
        return itemListDto;
    }

}
