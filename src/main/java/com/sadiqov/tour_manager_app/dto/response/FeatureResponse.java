package com.sadiqov.tour_manager_app.dto.response;


public record FeatureResponse(
        Long id,
        String title,
        String text,
        String imagePath,
        Integer rowOrder,
        String createdAt
) {}