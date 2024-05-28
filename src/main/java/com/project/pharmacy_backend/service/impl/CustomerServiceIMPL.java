package com.project.pharmacy_backend.service.impl;

import com.project.pharmacy_backend.dto.CustomerDTO;
import com.project.pharmacy_backend.entity.Customer;
import com.project.pharmacy_backend.repo.CustomerRepo;
import com.project.pharmacy_backend.service.CustomerService;
import com.project.pharmacy_backend.util.mappers.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceIMPL implements CustomerService {
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public String saveCustomer(CustomerDTO customerDTO) {
        System.out.println("Hello");
        System.out.println("Customer id"+customerDTO.getFirstName());
        Customer customer = customerMapper.customerDtoToCustomerEntity(customerDTO);

        customerRepo.save(customer);
        System.out.println("Customer id"+customer.getCustomerId());
        return customerDTO.getFirstName()+"Insert Successfully";
    }
}
