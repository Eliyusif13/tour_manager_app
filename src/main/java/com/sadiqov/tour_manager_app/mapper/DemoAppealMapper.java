package com.sadiqov.tour_manager_app.mapper;

import com.sadiqov.tour_manager_app.dto.DTORecords.DemoAppealRequest;
import com.sadiqov.tour_manager_app.dto.DTORecords.DemoAppealResponse;
import com.sadiqov.tour_manager_app.entity.DemoAppeal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PhoneNumberMapper.class, AddressMapper.class})
public interface DemoAppealMapper {
    DemoAppealMapper INSTANCE = Mappers.getMapper(DemoAppealMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "phoneNumbers", source = "phoneNumbers")
    @Mapping(target = "address", source = "address")
    DemoAppeal toEntity(DemoAppealRequest request);

    DemoAppealResponse toResponse(DemoAppeal demoAppeal);

    List<DemoAppealResponse> toResponseList(List<DemoAppeal> demoAppeals);
}