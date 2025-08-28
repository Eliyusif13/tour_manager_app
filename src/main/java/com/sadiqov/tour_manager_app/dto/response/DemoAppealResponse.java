package com.sadiqov.tour_manager_app.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record DemoAppealResponse(
        Long id,
        String fullName,
        String email,
        String companyName,
        String message,
        LocalDateTime createdAt,
        List<PhoneNumberResponse> phoneNumbers,
        AddressResponse address
) {}
