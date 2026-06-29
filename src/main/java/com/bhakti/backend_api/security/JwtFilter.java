package com.bhakti.backend_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getServletPath();

        if (
                path.equals("/auth/login")
                        || path.equals("/auth/register")
                        || path.startsWith("/swagger-ui")
                        || path.startsWith("/v3/api-docs")
                        || path.equals("/swagger-ui.html")
        ) {

            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }

        String authHeader =
                request.getHeader(
                        "Authorization"
                );

        if (
                authHeader == null
                        || !authHeader.startsWith("Bearer ")
        ) {

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );

            response.getWriter()
                    .write("Missing Token");

            return;
        }

        String token =
                authHeader.substring(7);

        try {

            Claims claims =
                    Jwts.parserBuilder()
                            .setSigningKey(
                                    JwtUtil.getKey()
                            )
                            .build()
                            .parseClaimsJws(token)
                            .getBody();

            String role =
                    claims.get(
                            "role",
                            String.class
                    );

            request.setAttribute(
                    "email",
                    claims.getSubject()
            );

            if (
                    (
                            path.equals("/auth/admin")
                                    || path.equals("/users")
                                    || (
                                    path.equals("/tasks")
                                            && request.getMethod().equals("GET")
                            )
                    )
                            && !"ADMIN".equals(role)
            ) {

                response.setStatus(
                        HttpServletResponse.SC_FORBIDDEN
                );

                response.getWriter()
                        .write("Access Denied");

                return;
            }

        } catch (Exception e) {

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );

            response.getWriter()
                    .write("Invalid Token");

            return;
        }

        filterChain.doFilter(
                request,
                response
        );
    }
}