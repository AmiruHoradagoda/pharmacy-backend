package com.project.pharmacy_backend.util.mappers;

import com.project.pharmacy_backend.dto.CustomerDTO;
import com.project.pharmacy_backend.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    //    customerDto -> customerEntity
    Customer customerDtoToCustomerEntity(CustomerDTO customerDTO);
}
