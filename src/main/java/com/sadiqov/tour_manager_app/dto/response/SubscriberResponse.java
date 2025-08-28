package com.sadiqov.tour_manager_app.dto.response;

import java.time.LocalDateTime;

public record SubscriberResponse(
        Long id,
        String email,
        LocalDateTime createdAt,
        Boolean isActive
) {}