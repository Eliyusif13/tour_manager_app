package com.sadiqov.tour_manager_app.dto.response;

public record PhoneNumberResponse(
        Long id,
        String phoneNumber,
        Long demoAppealId
) {}