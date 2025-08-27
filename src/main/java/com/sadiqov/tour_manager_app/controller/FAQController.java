package com.sadiqov.tour_manager_app.controller;

import com.sadiqov.tour_manager_app.dto.DTORecords.FAQRequest;
import com.sadiqov.tour_manager_app.dto.DTORecords.FAQResponse;
import com.sadiqov.tour_manager_app.service.FAQService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/faqs")
@RequiredArgsConstructor
public class FAQController {
    private final FAQService faqService;

    @GetMapping("/{lang}")
    public ResponseEntity<List<FAQResponse>> getAllFAQs(@PathVariable String lang) {
        return ResponseEntity.ok(faqService.getAllFAQs(lang));
    }

    @GetMapping("/{lang}/{id}")
    public ResponseEntity<FAQResponse> getFAQById(
            @PathVariable String lang,
            @PathVariable Long id) {
        Optional<FAQResponse> faq = faqService.getFAQById(id, lang);
        return faq.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createFAQ(@Valid @RequestBody FAQRequest request) {
        faqService.createFAQ(request);
        return ResponseEntity.ok(Map.of("message", "FAQ successfully created"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateFAQ(
            @PathVariable Long id,
            @Valid @RequestBody FAQRequest request) {
        faqService.updateFAQ(id, request);
        return ResponseEntity.ok(Map.of("message", "FAQ successfully updated"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteFAQ(@PathVariable Long id) {
        faqService.deleteFAQ(id);
        return ResponseEntity.ok(Map.of("message", "FAQ successfully deleted"));
    }
}