package com.sadiqov.tour_manager_app.config.token;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
@RequiredArgsConstructor
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        SecurityContextHolder.clearContext();

        String token = request.getHeader("Authorization");
        if (Objects.nonNull(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                String username = jwtUtil.getUsername(token);
                UserDetails user = userDetailsService.loadUserByUsername(username);
                if (jwtUtil.validateToken(token, user)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

}

