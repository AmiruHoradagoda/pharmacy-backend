package com.project.pharmacy_backend.util.mappers;

import com.project.pharmacy_backend.dto.request.ItemSaveRequestDTO;
import com.project.pharmacy_backend.dto.request.RequestOrderDetailsSave;
import com.project.pharmacy_backend.dto.response.ItemGetRequestDTO;
import com.project.pharmacy_backend.entity.Item;
import com.project.pharmacy_backend.entity.OrderItems;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {


    Item itemDtoToItemEntity(ItemSaveRequestDTO itemSaveRequestDTO);

    List<ItemGetRequestDTO> itemListToItemListDto(List<Item> itemList);
}
