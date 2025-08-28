package com.sadiqov.tour_manager_app.controller;

import com.sadiqov.tour_manager_app.dto.request.ContactUsRequest;
import com.sadiqov.tour_manager_app.dto.response.ContactUsResponse;
import com.sadiqov.tour_manager_app.dto.request.ContactUsUpdateRequest;
import com.sadiqov.tour_manager_app.service.ContactUsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        return ResponseEntity.ok(contactUsService.getContactRequestById(id)
                .orElseThrow(() -> new RuntimeException("Contact request not found")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> createContactRequest(@Valid @RequestBody ContactUsRequest request) {
        contactUsService.createContactRequest(request);
        return Map.of("message", "Contact request successfully created");
    }

    @PutMapping("/{id}")
    public Map<String, String> updateContactRequest(
            @PathVariable Long id,
            @Valid @RequestBody ContactUsUpdateRequest request) {
        contactUsService.updateContactRequest(id, request);
        return Map.of("message", "Contact request successfully updated");
    }

    @PatchMapping("/{id}/mark-responded")
    public Map<String, String> markAsResponded(@PathVariable Long id) {
        contactUsService.markAsResponded(id);
        return Map.of("message", "Contact request successfully marked as responded");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContactRequest(@PathVariable Long id) {
        contactUsService.deleteContactRequest(id);
    }

    @GetMapping("/count/unresponded")
    public ResponseEntity<Long> getUnrespondedCount() {
        return ResponseEntity.ok(contactUsService.getUnrespondedCount());
    }
}