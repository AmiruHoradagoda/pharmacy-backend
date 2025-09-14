package com.project.pharmacy_backend.service.impl;

import com.project.pharmacy_backend.dto.request.ItemSaveRequestDTO;
import com.project.pharmacy_backend.dto.response.ItemGetRequestDTO;
import com.project.pharmacy_backend.dto.response.pagination.ItemPaginationResponseDto;
import com.project.pharmacy_backend.entity.Item;
import com.project.pharmacy_backend.repo.ItemRepo;
import com.project.pharmacy_backend.service.ItemService;
import com.project.pharmacy_backend.util.mappers.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceIMPL implements ItemService {
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private ItemMapper itemMapper;

    public void initialItems() {
        addItemIfNotExists("Panadole", 100, 50, "https://www.grocerylanka.com/cdn/shop/products/Panadol-_Paracetamol-500mg_--Sri-Lanka_548x548.jpg?v=1643375348");
        addItemIfNotExists("Aspirin", 150, 30, "https://www.aspirin.ca/sites/g/files/vrxlpx30151/files/2021-06/Aspirin-81mg-tablets-30ct-carton_3.png");
        addItemIfNotExists("Tylenol", 200, 45, "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.shaws.com%2Fshop%2Fproduct-details.970113463.html&psig=AOvVaw2rxZQdSd7mY6OAn4GNOzb8&ust=1738860691027000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCOC44YL_rIsDFQAAAAAdAAAAABAR");
        addItemIfNotExists("Piriton", 180, 40, "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pillsorted.com%2Fproduct%2Fpiriton-allergy-tablets-60%2F&psig=AOvVaw21YN4LonI5uHc6jOk9yzfI&ust=1738860919240000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCICD3e7_rIsDFQAAAAAdAAAAABAp");
        addItemIfNotExists("Amoxicillin", 120, 100, "https://www.google.com/url?sa=i&url=https%3A%2F%2Fspmc.gov.lk%2Fproducts%2Fbeta-lactam%2Famoxiciilin-capsules-bp-500mg-blister-&psig=AOvVaw3NK3iwJPjteXoYIexjzrWK&ust=1738860735606000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCODg7pX_rIsDFQAAAAAdAAAAABAE");
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
        Item item = itemMapper.toItemEntity(itemSaveRequestDTO);
        itemRepo.save(item);
        return itemSaveRequestDTO.getItemName() + "Insert Successfully";
    }

    @Override
    public ItemPaginationResponseDto getAllItems(int page, int size, String sortBy, String sortDirection) {
        // Create sort direction
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        // Create sort object
        Sort sort = Sort.by(direction, sortBy);

        // Create pageable object
        Pageable pageable = PageRequest.of(page, size, sort);

        // Get paginated results
        Page<Item> itemPage = itemRepo.findAll(pageable);

        // Convert to DTOs
        List<ItemGetRequestDTO> itemListDto = itemMapper.toItemListDto(itemPage.getContent());

        // Create response
        ItemPaginationResponseDto response = new ItemPaginationResponseDto();
        response.setDataList(itemListDto);
        response.setDataCount(itemPage.getTotalElements());

        return response;
    }

    @Override
    public ItemGetRequestDTO getItemById(long itemId) {
        Item item = itemRepo.findById(itemId)
                .orElseThrow(() -> new RuntimeException(itemId + " item is not found..."));

        return itemMapper.toItemGetDto(item);
    }

    @Override
    public String deleteItemById(long itemId) {
        if (itemRepo.existsById(itemId)) {
            itemRepo.deleteById(itemId);
            return "Item with ID " + itemId + " deleted successfully.";
        } else {
            return "Item with ID " + itemId + " not found.";
        }
    }

    @Override
    public String editItemById(long itemId, ItemSaveRequestDTO itemSaveRequestDTO) {
        // Check if item exists
        Optional<Item> existingItemOptional = itemRepo.findById(itemId);

        if (existingItemOptional.isPresent()) {
            Item existingItem = existingItemOptional.get();

            // Update the existing item with new data
            existingItem.setItemName(itemSaveRequestDTO.getItemName());
            existingItem.setStockQuantity(itemSaveRequestDTO.getStockQuantity());
            existingItem.setItemPrice(itemSaveRequestDTO.getItemPrice());
            existingItem.setActiveState(itemSaveRequestDTO.isActiveState());

            // Update image URL only if provided
            if (itemSaveRequestDTO.getImageUrl() != null && !itemSaveRequestDTO.getImageUrl().trim().isEmpty()) {
                existingItem.setImageUrl(itemSaveRequestDTO.getImageUrl());
            }

            // Save the updated item
            itemRepo.save(existingItem);

            return "Item with ID " + itemId + " updated successfully.";
        } else {
            return "Item with ID " + itemId + " not found.";
        }
    }


}
