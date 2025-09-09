package com.project.pharmacy_backend.config;

import com.project.pharmacy_backend.service.JwtService;
import com.project.pharmacy_backend.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtService jwtService;

    // Define paths that should skip JWT validation
    private static final String[] EXCLUDED_PATHS = {
            "/api/v1/auth/authentication",
            "/api/v1/auth/register",
            "/api/v1/auth",
            "/api/public"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestPath = request.getRequestURI();
        String method = request.getMethod();

        System.out.println("=== JWT FILTER DEBUG ===");
        System.out.println("Request URI: " + requestPath);
        System.out.println("Request Method: " + method);
        System.out.println("Is Excluded: " + isExcludedPath(requestPath));
        System.out.println("========================");

        // Skip JWT validation for excluded paths
        if (isExcludedPath(requestPath)) {
            System.out.println("SKIPPING JWT validation for: " + requestPath);
            filterChain.doFilter(request, response);
            return;
        }

        final String requestTokenHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + requestTokenHeader);

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            System.out.println("JWT token does not start with Bearer for protected endpoint: " + requestPath);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isExcludedPath(String requestPath) {
        System.out.println("Checking exclusion for: " + requestPath);
        for (String excludedPath : EXCLUDED_PATHS) {
            System.out.println("  Comparing with: " + excludedPath + " -> " + requestPath.startsWith(excludedPath));
            if (requestPath.startsWith(excludedPath)) {
                return true;
            }
        }
        return false;
    }
}