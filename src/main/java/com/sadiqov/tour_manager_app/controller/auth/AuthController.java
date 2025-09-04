package com.sadiqov.tour_manager_app.controller.auth;

import com.sadiqov.tour_manager_app.dto.request.LoginRequest;
import com.sadiqov.tour_manager_app.dto.request.UserCreateRequest;
import com.sadiqov.tour_manager_app.service.auth.AuthService;
import com.sadiqov.tour_manager_app.service.auth.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody UserCreateRequest request) {
        userService.createUser(request);

    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(Map.of(
                "copy token.", token,
                "To log in with a token ,visit this link.", "http://localhost:8080/api/auth/token-login"
        ));
    }

    @PostMapping("/token-login")
    @ResponseStatus(HttpStatus.OK)
    public void loginWithToken(
            @RequestHeader("Authorization") String authHeader) {
        authService.loginWithToken(authHeader);


    }
}