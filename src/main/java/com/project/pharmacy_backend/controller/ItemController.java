package com.project.pharmacy_backend.controller;

import com.project.pharmacy_backend.dto.request.ItemSaveRequestDTO;
import com.project.pharmacy_backend.service.ItemService;
import com.project.pharmacy_backend.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("api/v1/item")
@CrossOrigin
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostConstruct
    public void initialItems(){
        itemService.initialItems();
    }


    @PostMapping(path="/save")
//    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<StandardResponse> saveItem(@RequestBody ItemSaveRequestDTO itemSaveRequestDTO){
        String message = itemService.saveItem(itemSaveRequestDTO);
        return new ResponseEntity<StandardResponse>(new StandardResponse(201,"Item is saved",message), HttpStatus.CREATED);
    }

//    @GetMapping(path = "item-list")
//    public ResponseEntity<StandardResponse> getItems(){
//        List<>
//    }
    
}
