package com.sadiqov.tour_manager_app.mapper;

import com.sadiqov.tour_manager_app.dto.request.PhoneNumberRequest;
import com.sadiqov.tour_manager_app.dto.response.PhoneNumberResponse;
import com.sadiqov.tour_manager_app.entity.home_page.PhoneNumber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PhoneNumberMapper {

    @Mapping(target = "demoAppeal", ignore = true)
    PhoneNumber toEntity(PhoneNumberRequest request);

    @Mapping(target = "demoAppealId", source = "demoAppeal.id")
    PhoneNumberResponse toResponse(PhoneNumber phoneNumber);

    List<PhoneNumberResponse> toResponseList(List<PhoneNumber> phoneNumbers);
}