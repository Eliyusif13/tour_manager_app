package com.sadiqov.tour_manager_app.mapper;

import com.sadiqov.tour_manager_app.dto.request.DemoAppealRequest;
import com.sadiqov.tour_manager_app.dto.response.DemoAppealResponse;
import com.sadiqov.tour_manager_app.entity.home_page.DemoAppeal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PhoneNumberMapper.class, AddressMapper.class})
public interface DemoAppealMapper {

    @Mapping(target = "phoneNumbers", source = "phoneNumbers")
    @Mapping(target = "address", source = "address")
    DemoAppeal toEntity(DemoAppealRequest request);

    DemoAppealResponse toResponse(DemoAppeal demoAppeal);

    List<DemoAppealResponse> toResponseList(List<DemoAppeal> demoAppeals);
}