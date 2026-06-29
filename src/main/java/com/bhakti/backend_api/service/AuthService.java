package com.bhakti.backend_api.service;

import com.bhakti.backend_api.dto.LoginRequest;
import com.bhakti.backend_api.dto.RegisterRequest;
import com.bhakti.backend_api.dto.RegisterResponse;
import com.bhakti.backend_api.entity.User;
import com.bhakti.backend_api.repository.UserRepository;
import com.bhakti.backend_api.security.JwtUtil;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public AuthService(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public RegisterResponse register(
            RegisterRequest request
    ) {

        User user = new User();

        user.setName(
                request.getName()
        );

        user.setEmail(
                request.getEmail()
        );

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole("USER");

        User savedUser =
                userRepository.save(user);

        RegisterResponse response =
                new RegisterResponse();

        response.setId(
                savedUser.getId()
        );

        response.setName(
                savedUser.getName()
        );

        response.setEmail(
                savedUser.getEmail()
        );

        response.setRole(
                savedUser.getRole()
        );

        return response;
    }

    public String login(
            LoginRequest request
    ) {

        User user =
                userRepository.findByEmail(
                        request.getEmail()
                );

        if (
                user == null ||
                request.getPassword() == null ||
                !passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                )
        ) {
            return "Invalid email or password";
        }

        return JwtUtil.generateToken(
                user.getEmail(),
                user.getRole()
        );
    }
}