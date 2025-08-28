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
    public void createContactRequest(@Valid @RequestBody ContactUsRequest request) {
        contactUsService.createContactRequest(request);
    }

    @PutMapping("/{id}")
    public void updateContactRequest(
            @PathVariable Long id,
            @Valid @RequestBody ContactUsUpdateRequest request) {
        contactUsService.updateContactRequest(id, request);
    }

    @PatchMapping("/{id}/mark-responded")
    public void markAsResponded(@PathVariable Long id) {
        contactUsService.markAsResponded(id);
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