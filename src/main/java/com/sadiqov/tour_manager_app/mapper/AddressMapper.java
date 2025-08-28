package com.sadiqov.tour_manager_app.mapper;

import com.sadiqov.tour_manager_app.dto.request.AddressRequest;
import com.sadiqov.tour_manager_app.dto.response.AddressResponse;
import com.sadiqov.tour_manager_app.entity.home_page.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "demoAppeal", ignore = true)
    Address toEntity(AddressRequest request);

    @Mapping(target = "demoAppealId", source = "demoAppeal.id")
    AddressResponse toResponse(Address address);

    List<AddressResponse> toResponseList(List<Address> addresses);
}