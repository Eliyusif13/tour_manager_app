package com.sadiqov.tour_manager_app.controller.admin;

import com.sadiqov.tour_manager_app.dto.request.FAQRequest;
import com.sadiqov.tour_manager_app.dto.response.FAQResponse;
import com.sadiqov.tour_manager_app.service.FAQService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/faqs")
@RequiredArgsConstructor
public class AdminFAQController {
    private final FAQService faqService;

    @GetMapping
    public ResponseEntity<List<FAQResponse>> getAllFAQs() {
        return ResponseEntity.ok(faqService.getAllFAQs("az")); // Default language
    }

    @GetMapping("/{id}")
    public ResponseEntity<FAQResponse> getFAQById(@PathVariable Long id) {
        return ResponseEntity.ok(faqService.getFAQById(id, "az")
                .orElseThrow(() -> new RuntimeException("FAQ not found")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> createFAQ(@Valid @RequestBody FAQRequest request) {
        faqService.createFAQ(request);
        return Map.of("message", "FAQ successfully created");
    }

    @PutMapping("/{id}")
    public Map<String, String> updateFAQ(
            @PathVariable Long id,
            @Valid @RequestBody FAQRequest request) {
        faqService.updateFAQ(id, request);
        return Map.of("message", "FAQ successfully updated");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFAQ(@PathVariable Long id) {
        faqService.deleteFAQ(id);
    }
}