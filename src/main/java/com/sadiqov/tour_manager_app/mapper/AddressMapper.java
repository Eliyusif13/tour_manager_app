package com.sadiqov.tour_manager_app.mapper;

import com.sadiqov.tour_manager_app.dto.DTORecords.*;
import com.sadiqov.tour_manager_app.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "demoAppeal", ignore = true)
    Address toEntity(AddressRequest request);

    @Mapping(target = "demoAppealId", source = "demoAppeal.id")
    AddressResponse toResponse(Address address);

    java.util.List<AddressResponse> toResponseList(java.util.List<Address> addresses);
}