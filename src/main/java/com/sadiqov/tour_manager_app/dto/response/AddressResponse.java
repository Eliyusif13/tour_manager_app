package com.sadiqov.tour_manager_app.dto.response;

public record AddressResponse(
        Long id,
        String addressAz,
        String addressEn,
        String addressRu,
        Long demoAppealId
) {}
