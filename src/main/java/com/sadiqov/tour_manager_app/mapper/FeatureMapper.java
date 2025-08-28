package com.sadiqov.tour_manager_app.mapper;

import com.sadiqov.tour_manager_app.dto.request.FeatureRequest;
import com.sadiqov.tour_manager_app.dto.response.FeatureResponse;
import com.sadiqov.tour_manager_app.entity.feature.Feature;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeatureMapper {

    @Mapping(target = "imagePath", ignore = true)
    Feature toEntity(FeatureRequest request);

    @Named("toAzResponse")
    default FeatureResponse toAzResponse(Feature feature) {
        return new FeatureResponse(
                feature.getId(),
                feature.getTitleAz(),
                feature.getTextAz(),
                feature.getImagePath(),
                feature.getRowOrder(),
                feature.getCreatedAt().toString()
        );
    }

    @Named("toRuResponse")
    default FeatureResponse toRuResponse(Feature feature) {
        return new FeatureResponse(
                feature.getId(),
                feature.getTitleRu(),
                feature.getTextRu(),
                feature.getImagePath(),
                feature.getRowOrder(),
                feature.getCreatedAt().toString()
        );
    }

    @Named("toEnResponse")
    default FeatureResponse toEnResponse(Feature feature) {
        return new FeatureResponse(
                feature.getId(),
                feature.getTitleEn(),
                feature.getTextEn(),
                feature.getImagePath(),
                feature.getRowOrder(),
                feature.getCreatedAt().toString()
        );
    }

    default FeatureResponse toResponse(Feature feature, String lang) {
        return switch (lang.toLowerCase()) {
            case "ru" -> toRuResponse(feature);
            case "en" -> toEnResponse(feature);
            default -> toAzResponse(feature);
        };
    }

    default List<FeatureResponse> toResponseList(List<Feature> features, String lang) {
        return features.stream()
                .map(feature -> toResponse(feature, lang))
                .toList();
    }
}