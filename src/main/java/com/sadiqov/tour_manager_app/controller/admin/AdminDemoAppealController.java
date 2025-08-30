package com.sadiqov.tour_manager_app.controller.admin;

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
@RequestMapping("/api/admin/demo-appeals")
@RequiredArgsConstructor
public class AdminDemoAppealController {
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
    public Map<String, String> createDemoAppeal(
            @Valid @RequestBody DemoAppealRequest request) {
        demoAppealService.createDemoAppeal(request);
        return Map.of("message", "Demo request successfully created");
    }

    @PutMapping("/{id}")
    public Map<String, String> updateDemoAppeal(
            @PathVariable Long id,
            @Valid @RequestBody DemoAppealRequest request) {
        demoAppealService.updateDemoAppeal(id, request);
        return Map.of("message", "Demo appeal successfully updated");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDemoAppeal(@PathVariable Long id) {
        demoAppealService.deleteDemoAppeal(id);
    }
}