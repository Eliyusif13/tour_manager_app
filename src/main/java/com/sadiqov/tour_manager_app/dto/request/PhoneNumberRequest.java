package com.sadiqov.tour_manager_app.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PhoneNumberRequest(
        @NotBlank(message = "Phone number is required")
        String phoneNumber
) {}
