package com.project.pharmacy_backend.service.impl;

import com.project.pharmacy_backend.dto.request.RequestOrderDetailsSave;
import com.project.pharmacy_backend.dto.request.RequestOrderSaveDTO;
import com.project.pharmacy_backend.dto.response.OrderGetResponseDto;
import com.project.pharmacy_backend.dto.response.UserGetResponseDto;
import com.project.pharmacy_backend.dto.response.pagination.OrderPaginateResponseDto;
import com.project.pharmacy_backend.entity.*;
import com.project.pharmacy_backend.repo.*;
import com.project.pharmacy_backend.service.OrderService;
import com.project.pharmacy_backend.util.enums.OrderStatus;
import com.project.pharmacy_backend.util.mappers.ShippingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private UserRepo customerRepo;

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
        User customer = customerRepo.findByEmail(requestOrderSaveDTO.getCustomer())
                .orElseThrow(() -> new RuntimeException("Customer not found with email: " + requestOrderSaveDTO.getCustomer()));
        ShippingAddress shippingAddress = shippingMapper.shippingAddressDtoToShippingEntity(requestOrderSaveDTO.getRequestShippingAddressSave());
        shippingRepo.save(shippingAddress);

        // Create the order entity
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(requestOrderSaveDTO.getOrderDate());
        order.setTotalAmount(requestOrderSaveDTO.getTotalAmount());
        order.setStatus(OrderStatus.PENDING);
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

    @Override
    public OrderPaginateResponseDto getAllOrders(int page, int size, String sortBy, String sortDirection) {
        // Create sort direction
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        // Create sort object
        Sort sort = Sort.by(direction, sortBy);

        // Create pageable object
        Pageable pageable = PageRequest.of(page, size, sort);

        // Get paginated results
        Page<Order> orderPage = orderRepo.findAll(pageable);

        // Convert Order entities to OrderGetResponseDto
        List<OrderGetResponseDto> orderGetResponseDtoList = new ArrayList<>();

        for (Order order : orderPage.getContent()) {
            // Create UserGetResponseDto for customer
            UserGetResponseDto customerDto = UserGetResponseDto.builder()
                    .userId(order.getCustomer().getUserId())
                    .firstName(order.getCustomer().getFirstName())
                    .lastName(order.getCustomer().getLastName())
                    .email(order.getCustomer().getEmail())
                    .phoneNumber(order.getCustomer().getPhoneNumber())
                    .build();

            // Create OrderGetResponseDto
            OrderGetResponseDto orderGetResponseDto = OrderGetResponseDto.builder()
                    .orderId(order.getOrderId())
                    .customer(customerDto)
                    .orderDate(order.getOrderDate())
                    .status(order.getStatus())
                    .totalAmount(order.getTotalAmount())
                    .build();

            orderGetResponseDtoList.add(orderGetResponseDto);
        }

        // Create and return OrderPaginateResponseDto with pagination info
        return OrderPaginateResponseDto.builder()
                .dataList(orderGetResponseDtoList)
                .dataCount(orderPage.getTotalElements())
                .build();
    }


}

