package com.sadiqov.tour_manager_app.controller;

import com.sadiqov.tour_manager_app.dto.request.FeatureRequest;
import com.sadiqov.tour_manager_app.dto.response.FeatureResponse;
import com.sadiqov.tour_manager_app.service.FeatureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/features")
@RequiredArgsConstructor
public class FeatureController {
    private final FeatureService featureService;

    @GetMapping("/{lang}")
    public ResponseEntity<List<FeatureResponse>> getAllFeatures(@PathVariable String lang) {
        return ResponseEntity.ok(featureService.getAllFeatures(lang));
    }

    @GetMapping("/{lang}/{id}")
    public ResponseEntity<FeatureResponse> getFeatureById(
            @PathVariable String lang,
            @PathVariable Long id) {
        return ResponseEntity.ok(featureService.getFeatureById(id, lang));
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createFeature(@Valid @RequestBody FeatureRequest request) {
        featureService.createFeature(request);
        return ResponseEntity.ok(Map.of("message", "Feature successfully created"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateFeature(
            @PathVariable Long id,
            @Valid @RequestBody FeatureRequest request) {
        featureService.updateFeature(id, request);
        return ResponseEntity.ok(Map.of("message", "Feature successfully updated"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteFeature(@PathVariable Long id) {
        featureService.deleteFeature(id);
        return ResponseEntity.ok(Map.of("message", "Feature successfully deleted"));
    }
}