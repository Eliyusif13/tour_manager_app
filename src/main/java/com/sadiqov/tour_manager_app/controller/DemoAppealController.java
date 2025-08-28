package com.sadiqov.tour_manager_app.controller;

import com.sadiqov.tour_manager_app.dto.request.DemoAppealRequest;
import com.sadiqov.tour_manager_app.dto.response.DemoAppealResponse;
import com.sadiqov.tour_manager_app.service.DemoAppealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public void createDemoAppeal(
            @Valid @RequestBody DemoAppealRequest request) {
        demoAppealService.createDemoAppeal(request);
    }

    @PutMapping("/{id}")
    public void updateDemoAppeal(
            @PathVariable Long id,
            @Valid @RequestBody DemoAppealRequest request) {
        demoAppealService.updateDemoAppeal(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDemoAppeal(@PathVariable Long id) {
        demoAppealService.deleteDemoAppeal(id);
    }
}