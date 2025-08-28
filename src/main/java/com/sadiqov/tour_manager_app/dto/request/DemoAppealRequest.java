package com.sadiqov.tour_manager_app.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record DemoAppealRequest(
        @NotBlank(message = "Full name is required")
        String fullName,

        @NotBlank(message = "Email is required")
        @Email(message = "Please provide a valid email address")
        String email,

        String companyName,
        String message,

        List<PhoneNumberRequest> phoneNumbers,
        AddressRequest address
) {}
