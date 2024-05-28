package com.project.pharmacy_backend.util.mappers;

import com.project.pharmacy_backend.dto.request.RequestShippingAddressSave;
import com.project.pharmacy_backend.entity.ShippingAddress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShippingMapper {
    ShippingAddress shippingAddressDtoToShippingEntity(RequestShippingAddressSave requestShippingAddressSave);
}
