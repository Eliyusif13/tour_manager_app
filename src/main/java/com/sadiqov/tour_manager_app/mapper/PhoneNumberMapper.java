package com.sadiqov.tour_manager_app.mapper;

import com.sadiqov.tour_manager_app.dto.DTORecords.*;
import com.sadiqov.tour_manager_app.entity.home_page.PhoneNumber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PhoneNumberMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "demoAppeal", ignore = true)
    PhoneNumber toEntity(PhoneNumberRequest request);

    @Mapping(target = "demoAppealId", source = "demoAppeal.id")
    PhoneNumberResponse toResponse(PhoneNumber phoneNumber);

    java.util.List<PhoneNumberResponse> toResponseList(java.util.List<PhoneNumber> phoneNumbers);
}