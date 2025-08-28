package com.sadiqov.tour_manager_app.mapper;

import com.sadiqov.tour_manager_app.dto.request.ContactUsRequest;
import com.sadiqov.tour_manager_app.dto.response.ContactUsResponse;
import com.sadiqov.tour_manager_app.dto.request.ContactUsUpdateRequest;
import com.sadiqov.tour_manager_app.entity.home_page.ContactUs;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactUsMapper {

    @Mapping(target = "isResponded", constant = "false")
    ContactUs toEntity(ContactUsRequest request);

    ContactUsResponse toResponse(ContactUs contactUs);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(ContactUsUpdateRequest request, @MappingTarget ContactUs contactUs);

    List<ContactUsResponse> toResponseList(List<ContactUs> contactUsList);
}