package com.sadiqov.tour_manager_app.controller;

import com.sadiqov.tour_manager_app.dto.DTORecords.DemoAppealRequest;
import com.sadiqov.tour_manager_app.service.DemoAppealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/demo-appeals")
@RequiredArgsConstructor
public class DemoAppealController {
    private final DemoAppealService demoAppealService;


    @PostMapping
    public ResponseEntity<Map<String, String>> createDemoAppeal(
            @Valid @RequestBody DemoAppealRequest request) {
        demoAppealService.createDemoAppeal(request);
        return ResponseEntity.ok(Map.of("message", "Demo request successfully created"));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateDemoAppeal(
            @PathVariable Long id,
            @Valid @RequestBody DemoAppealRequest request) {
        demoAppealService.updateDemoAppeal(id, request);
        return ResponseEntity.ok(Map.of("message", "Demo appeal successfully updated"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteDemoAppeal(@PathVariable Long id) {
        demoAppealService.deleteDemoAppeal(id);
        return ResponseEntity.ok(Map.of("message", "Demo appeal successfully deleted"));
    }

}