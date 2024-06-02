package com.project.pharmacy_backend.util.mappers;

import com.project.pharmacy_backend.dto.UserDTO;
import com.project.pharmacy_backend.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //    customerDto -> customerEntity
    User UserDtoToUserEntity(UserDTO userDTO);
}
