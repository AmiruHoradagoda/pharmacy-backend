package com.project.pharmacy_backend.service.impl;

import com.project.pharmacy_backend.dto.request.RequestOrderDetailsSave;
import com.project.pharmacy_backend.dto.request.RequestOrderSaveDTO;
import com.project.pharmacy_backend.entity.*;
import com.project.pharmacy_backend.repo.*;
import com.project.pharmacy_backend.service.OrderService;
import com.project.pharmacy_backend.util.mappers.ShippingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class OrderServiceIMPL implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private ShippingRepo shippingRepo;

    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private OrderDetailRepo orderDetailRepo;

    @Override
    @Transactional
    public String addOrder(RequestOrderSaveDTO requestOrderSaveDTO) {
        // Retrieve customer entity from the repository
        Customer customer = customerRepo.getById(requestOrderSaveDTO.getCustomer());
        ShippingAddress shippingAddress = shippingMapper.shippingAddressDtoToShippingEntity(requestOrderSaveDTO.getRequestShippingAddressSave());
        shippingRepo.save(shippingAddress);

        // Create the order entity
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(requestOrderSaveDTO.getOrderDate());
        order.setTotalAmount(requestOrderSaveDTO.getTotalAmount());
        order.setShippingAddress(shippingAddress);

        // Save the order entity first to generate the order ID
        orderRepo.save(order);

        // Map and save order items
        List<OrderItems> orderItemsList = new ArrayList<>();
        for (RequestOrderDetailsSave orderDetails : requestOrderSaveDTO.getOrderDetailsSaves()) {
            OrderItems orderItem = new OrderItems();
            orderItem.setAmount(orderDetails.getAmount());
            orderItem.setItemName(orderDetails.getItemName());
            orderItem.setQuantity(orderDetails.getQuantity());

            // Retrieve item entity from the repository and set it
            Item item = itemRepo.getById(orderDetails.getItemId());
            orderItem.setItems(item);
            orderItem.setOrders(order); // Set the order reference

            orderItemsList.add(orderItem);
        }

        // Save the order items if there are any
        if (!orderItemsList.isEmpty()) {
            orderDetailRepo.saveAll(orderItemsList);
        }

        return "Order saved successfully with ID: " + order.getOrderId();
    }
}

