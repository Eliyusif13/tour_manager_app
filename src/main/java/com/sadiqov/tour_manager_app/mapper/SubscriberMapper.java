package com.sadiqov.tour_manager_app.mapper;

import com.sadiqov.tour_manager_app.dto.DTORecords.*;
import com.sadiqov.tour_manager_app.entity.Subscriber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SubscriberMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isActive", constant = "true")
    Subscriber toEntity(SubscriberResponse request);

    SubscriberResponse toResponse(Subscriber subscriber);

    List<SubscriberResponse> toResponseList(java.util.List<Subscriber> subscribers);
}