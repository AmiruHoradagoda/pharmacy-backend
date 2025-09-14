package com.project.pharmacy_backend.controller;

import com.project.pharmacy_backend.dto.request.ItemSaveRequestDTO;
import com.project.pharmacy_backend.dto.response.ItemGetRequestDTO;
import com.project.pharmacy_backend.dto.response.pagination.ItemPaginationResponseDto;
import com.project.pharmacy_backend.service.ItemService;
import com.project.pharmacy_backend.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("api/v1/item")
@CrossOrigin
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostConstruct
    public void initialItems() {
        itemService.initialItems();
    }


    @PostMapping(path = "/save")
//    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<StandardResponse> saveItem(@RequestBody ItemSaveRequestDTO itemSaveRequestDTO) {
        String message = itemService.saveItem(itemSaveRequestDTO);
        return new ResponseEntity<StandardResponse>(new StandardResponse(201, "Item is saved", message), HttpStatus.CREATED);
    }

    @GetMapping(path = "/item-list")
    public ResponseEntity<StandardResponse> getAllItems(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int size,
                                                        @RequestParam(defaultValue = "itemId") String sortBy,
                                                        @RequestParam(defaultValue = "asc") String sortDirection) {
        ItemPaginationResponseDto allItems = itemService.getAllItems(page, size, sortBy, sortDirection);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200, "Items are retrieved", allItems),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "/by-id/{id}")
    public ResponseEntity<StandardResponse> getItemById(@PathVariable("id") long itemId) {
        ItemGetRequestDTO selectedItem = itemService.getItemById(itemId);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Item " + itemId + " is saved", selectedItem), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<StandardResponse> editItemById(
            @PathVariable("id") long itemId,
            @RequestBody ItemSaveRequestDTO itemSaveRequestDTO) {

        String message = itemService.editItemById(itemId, itemSaveRequestDTO);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200, "Item " + itemId + " is Updated", message),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<StandardResponse> deleteItemById(@PathVariable("id") long itemId) {
        String message = itemService.deleteItemById(itemId);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Item " + itemId + " is deleted", message), HttpStatus.OK);
    }

}
