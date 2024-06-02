package com.project.pharmacy_backend.dto.request;

import com.project.pharmacy_backend.entity.ShippingAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestOrderSaveDTO {
   
   
    private String customer;

    private Date orderDate;

    private double totalAmount;

    private List<RequestOrderDetailsSave>orderDetailsSaves;

    private RequestShippingAddressSave requestShippingAddressSave;
}
