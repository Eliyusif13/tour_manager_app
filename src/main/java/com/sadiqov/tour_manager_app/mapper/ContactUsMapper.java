package com.sadiqov.tour_manager_app.mapper;

import com.sadiqov.tour_manager_app.dto.DTORecords.*;
import com.sadiqov.tour_manager_app.entity.ContactUs;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContactUsMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isResponded", constant = "false")
    ContactUs toEntity(ContactUsRequest request);

    ContactUsResponse toResponse(ContactUs contactUs);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromRequest(ContactUsUpdateRequest request, @MappingTarget ContactUs contactUs);

    java.util.List<ContactUsResponse> toResponseList(java.util.List<ContactUs> contactUsList);
}