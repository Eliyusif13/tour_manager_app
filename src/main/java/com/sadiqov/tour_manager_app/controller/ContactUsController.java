package com.sadiqov.tour_manager_app.controller;

import com.sadiqov.tour_manager_app.dto.DTORecords.*;
import com.sadiqov.tour_manager_app.service.ContactUsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/contact-requests")
@RequiredArgsConstructor
public class ContactUsController {
    private final ContactUsService contactUsService;

    @GetMapping
    public ResponseEntity<List<ContactUsResponse>> getAllContactRequests() {
        return ResponseEntity.ok(contactUsService.getAllContactRequests());
    }

    @GetMapping("/unresponded")
    public ResponseEntity<List<ContactUsResponse>> getUnrespondedContactRequests() {
        return ResponseEntity.ok(contactUsService.getUnrespondedContactRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactUsResponse> getContactRequestById(@PathVariable Long id) {
        return contactUsService.getContactRequestById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<?> createContactRequest(@Valid @RequestBody ContactUsRequest request) {
        try {
            contactUsService.createContactRequest(request);
            return ResponseEntity.ok(Map.of("message", "Contact request successfully created"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateContactRequest(
            @PathVariable Long id,
            @Valid @RequestBody ContactUsUpdateRequest request) {
        contactUsService.updateContactRequest(id, request);
        return ResponseEntity.ok(Map.of("message", "Contact request successfully updated"));
    }

    @PatchMapping("/{id}/mark-responded")
    public ResponseEntity<Map<String, String>> markAsResponded(@PathVariable Long id) {
        contactUsService.markAsResponded(id);
        return ResponseEntity.ok(Map.of("message", "Contact request successfully marked as responded"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteContactRequest(@PathVariable Long id) {
        contactUsService.deleteContactRequest(id);
        return ResponseEntity.ok(Map.of("message", "Contact request successfully deleted"));
    }

    @GetMapping("/count/unresponded")
    public ResponseEntity<Long> getUnrespondedCount() {
        return ResponseEntity.ok(contactUsService.getUnrespondedCount());
    }
}