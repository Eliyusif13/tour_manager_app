package com.sadiqov.tour_manager_app.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SubscriberRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Please provide a valid email address")
        String email
) {}
