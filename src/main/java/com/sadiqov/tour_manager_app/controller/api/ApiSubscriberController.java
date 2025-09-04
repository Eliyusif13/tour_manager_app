package com.sadiqov.tour_manager_app.controller.api;

import com.sadiqov.tour_manager_app.dto.request.SubscriberRequest;
import com.sadiqov.tour_manager_app.service.adminAndApi.SubscriberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/public/subscribers")
@RequiredArgsConstructor
public class ApiSubscriberController {
    private final SubscriberService subscriberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> createSubscriber(@Valid @RequestBody SubscriberRequest request) {
        subscriberService.createSubscriber(request);
        return Map.of("message", "Subscriber successfully created");
    }
}