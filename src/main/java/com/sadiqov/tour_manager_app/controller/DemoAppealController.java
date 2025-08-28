package com.sadiqov.tour_manager_app.controller;

import com.sadiqov.tour_manager_app.dto.request.DemoAppealRequest;
import com.sadiqov.tour_manager_app.dto.response.DemoAppealResponse;
import com.sadiqov.tour_manager_app.service.DemoAppealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/demo-appeals")
@RequiredArgsConstructor
public class DemoAppealController {
    private final DemoAppealService demoAppealService;

    @GetMapping
    public ResponseEntity<List<DemoAppealResponse>> getAllDemoAppeals() {
        return ResponseEntity.ok(demoAppealService.getAllDemoAppeals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DemoAppealResponse> getDemoAppealById(@PathVariable Long id) {
        return ResponseEntity.ok(demoAppealService.getDemoAppealById(id));
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createDemoAppeal(
            @Valid @RequestBody DemoAppealRequest request) {
        DemoAppealResponse response = demoAppealService.createDemoAppeal(request);
        return ResponseEntity.ok(Map.of(
                "message", "Demo request successfully created",
                "id", response.id().toString()
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateDemoAppeal(
            @PathVariable Long id,
            @Valid @RequestBody DemoAppealRequest request) {
        DemoAppealResponse response = demoAppealService.updateDemoAppeal(id, request);
        return ResponseEntity.ok(Map.of(
                "message", "Demo appeal successfully updated",
                "id", response.id().toString()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteDemoAppeal(@PathVariable Long id) {
        demoAppealService.deleteDemoAppeal(id);
        return ResponseEntity.ok(Map.of("message", "Demo appeal successfully deleted"));
    }
}