package com.sadiqov.tour_manager_app.dto.request;

public record AddressRequest(
        String addressAz,
        String addressEn,
        String addressRu
) {}