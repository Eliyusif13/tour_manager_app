package com.sadiqov.tour_manager_app.controller.api;

import com.sadiqov.tour_manager_app.dto.response.FeatureResponse;
import com.sadiqov.tour_manager_app.service.FeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/features")
@RequiredArgsConstructor
public class ApiFeatureController {
    private final FeatureService featureService;

    @GetMapping
    public ResponseEntity<List<FeatureResponse>> getFeaturesByLanguage(
            @RequestHeader(value = "Accept-Language", defaultValue = "az") String lang) {
        return ResponseEntity.ok(featureService.getAllFeatures(lang));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeatureResponse> getFeatureById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "az") String lang) {
        return ResponseEntity.ok(featureService.getFeatureById(id, lang));
    }
}