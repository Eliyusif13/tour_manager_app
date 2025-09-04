package com.sadiqov.tour_manager_app.controller.admin;

import com.sadiqov.tour_manager_app.dto.response.SubscriberResponse;
import com.sadiqov.tour_manager_app.service.adminAndApi.SubscriberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/subscribers")
@RequiredArgsConstructor
public class AdminSubscriberController {
    private final SubscriberService subscriberService;

    @GetMapping
    public ResponseEntity<List<SubscriberResponse>> getAllSubscribers() {
        return ResponseEntity.ok(subscriberService.getAllSubscribers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriberResponse> getSubscriberById(@PathVariable Long id) {
        return ResponseEntity.ok(subscriberService.getSubscriberById(id)
                .orElseThrow(() -> new RuntimeException("Subscriber not found")));
    }

    @PatchMapping("/{id}/deactivate")
    public Map<String, String> deactivateSubscriber(@PathVariable Long id) {
        subscriberService.deactivateSubscriber(id);
        return Map.of("message", "Subscriber successfully deactivated");
    }

    @PatchMapping("/{id}/activate")
    public Map<String, String> activateSubscriber(@PathVariable Long id) {
        subscriberService.activateSubscriber(id);
        return Map.of("message", "Subscriber successfully activated");
    }

    @GetMapping("/count/active")
    public ResponseEntity<Long> getActiveSubscribersCount() {
        return ResponseEntity.ok(subscriberService.getActiveSubscribersCount());
    }

    @GetMapping("/count/all")
    public ResponseEntity<Long> getAllSubscribersCount() {
        return ResponseEntity.ok(subscriberService.getAllSubscribersCount());
    }
}