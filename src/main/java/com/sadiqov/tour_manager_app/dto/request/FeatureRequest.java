package com.sadiqov.tour_manager_app.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record FeatureRequest(
        String titleAz,
        String titleEn,
        String titleRu,
        String textAz,
        String textEn,
        String textRu,
        MultipartFile image,
        Integer rowOrder
) {}