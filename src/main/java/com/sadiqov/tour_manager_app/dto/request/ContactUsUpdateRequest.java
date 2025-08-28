package com.sadiqov.tour_manager_app.dto.request;

import jakarta.validation.constraints.*;

public record ContactUsUpdateRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Please provide a valid email address")
        String email,

        @NotBlank(message = "Message is required")
        String message,

        Boolean isResponded
) {}