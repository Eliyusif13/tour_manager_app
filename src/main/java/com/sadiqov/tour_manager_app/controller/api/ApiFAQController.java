package com.sadiqov.tour_manager_app.controller.api;

import com.sadiqov.tour_manager_app.dto.response.FAQResponse;
import com.sadiqov.tour_manager_app.service.adminAndApi.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/faqs")
@RequiredArgsConstructor
public class ApiFAQController {
    private final FAQService faqService;

    @GetMapping
    public ResponseEntity<List<FAQResponse>> getFAQsByLanguage(
            @RequestHeader(value = "Accept-Language", defaultValue = "az") String lang) {
        return ResponseEntity.ok(faqService.getAllFAQs(lang));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FAQResponse> getFAQById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "az") String lang) {
        return ResponseEntity.ok(faqService.getFAQById(id, lang)
                .orElseThrow(() -> new RuntimeException("FAQ not found")));
    }
}