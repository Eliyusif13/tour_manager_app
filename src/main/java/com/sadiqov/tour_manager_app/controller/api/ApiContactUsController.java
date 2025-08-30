package com.sadiqov.tour_manager_app.controller.api;

import com.sadiqov.tour_manager_app.dto.request.ContactUsRequest;
import com.sadiqov.tour_manager_app.service.ContactUsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/public/contact-requests")
@RequiredArgsConstructor
public class ApiContactUsController {
    private final ContactUsService contactUsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> createContactRequest(@Valid @RequestBody ContactUsRequest request) {
        contactUsService.createContactRequest(request);
        return Map.of("message", "Contact request successfully created");
    }
}