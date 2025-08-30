package com.sadiqov.tour_manager_app.controller.admin;

import com.sadiqov.tour_manager_app.dto.request.FeatureRequest;
import com.sadiqov.tour_manager_app.dto.response.FeatureResponse;
import com.sadiqov.tour_manager_app.service.FeatureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/features")
@RequiredArgsConstructor
public class AdminFeatureController {
    private final FeatureService featureService;

    @GetMapping
    public ResponseEntity<List<FeatureResponse>> getAllFeatures() {
        return ResponseEntity.ok(featureService.getAllFeatures("az")); // Default language
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeatureResponse> getFeatureById(@PathVariable Long id) {
        return ResponseEntity.ok(featureService.getFeatureById(id, "az"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> createFeature(@Valid @RequestBody FeatureRequest request) {
        featureService.createFeature(request);
        return Map.of("message", "Feature successfully created");
    }

    @PutMapping("/{id}")
    public Map<String, String> updateFeature(
            @PathVariable Long id,
            @Valid @RequestBody FeatureRequest request) {
        featureService.updateFeature(id, request);
        return Map.of("message", "Feature successfully updated");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFeature(@PathVariable Long id) {
        featureService.deleteFeature(id);
    }
}