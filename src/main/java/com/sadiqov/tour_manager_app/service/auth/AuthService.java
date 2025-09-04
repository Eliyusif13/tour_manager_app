package com.sadiqov.tour_manager_app.service.auth;

import com.sadiqov.tour_manager_app.config.token.JwtUtil;
import com.sadiqov.tour_manager_app.dto.request.LoginRequest;
import com.sadiqov.tour_manager_app.entity.user.User;
import com.sadiqov.tour_manager_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public String login(LoginRequest request) {
        String username = getUsernameFromRequest(request.getUsernameOrEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtil.generateToken(username);
    }

    public void loginWithToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid authorization header");
        }

        String token = authHeader.substring(7);
        String username = jwtUtil.getUsername(token);

        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Invalid token");
        }


        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getIsActive()) {
            throw new RuntimeException("User is not active");
        }

        jwtUtil.generateToken(username);
    }

    private String getUsernameFromRequest(String usernameOrEmail) {
        if (usernameOrEmail.contains("@")) {
            User user = userRepository.findByEmail(usernameOrEmail)
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + usernameOrEmail));
            return user.getUsername();
        }
        return usernameOrEmail;
    }

}