package com.sadiqov.tour_manager_app.dto.response;

import java.time.LocalDateTime;

public record ContactUsResponse(
        Long id,
        String name,
        String email,
        String message,
        LocalDateTime createdAt,
        Boolean isResponded
) {}
