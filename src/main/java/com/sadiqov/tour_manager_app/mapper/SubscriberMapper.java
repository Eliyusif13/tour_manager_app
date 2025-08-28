package com.sadiqov.tour_manager_app.mapper;

import com.sadiqov.tour_manager_app.dto.request.SubscriberRequest;
import com.sadiqov.tour_manager_app.dto.response.SubscriberResponse;
import com.sadiqov.tour_manager_app.entity.home_page.Subscriber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubscriberMapper {

    @Mapping(target = "isActive", constant = "true")
    Subscriber toEntity(SubscriberRequest request);

    SubscriberResponse toResponse(Subscriber subscriber);

    List<SubscriberResponse> toResponseList(List<Subscriber> subscribers);
}