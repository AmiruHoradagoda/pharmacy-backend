package com.project.pharmacy_backend.dto.request;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestOrderDetailsSave {
    
   
    private double amount;

    private String itemName;

    private int quantity;

    //item id
    private Long itemId;
   
}
