package com.bhakti.backend_api.controller;

import com.bhakti.backend_api.dto.LoginRequest;
import com.bhakti.backend_api.dto.RegisterRequest;
import com.bhakti.backend_api.dto.RegisterResponse;
import com.bhakti.backend_api.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(
            AuthService authService
    ) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public RegisterResponse register(
            @Valid @RequestBody RegisterRequest request
    ) {

        System.out.println(
                "NAME = " + request.getName()
        );

        return authService.register(
                request
        );
    }

    @PostMapping("/login")
    public String login(
            @Valid @RequestBody LoginRequest request
    ) {
        return authService.login(
                request
        );
    }

    @GetMapping("/admin")
    public String adminAccess() {

        return "Welcome Admin";
    }
}