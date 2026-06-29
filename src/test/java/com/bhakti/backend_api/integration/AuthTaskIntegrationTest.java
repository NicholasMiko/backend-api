package com.bhakti.backend_api.integration;

import com.bhakti.backend_api.entity.User;
import com.bhakti.backend_api.repository.UserRepository;
import com.bhakti.backend_api.security.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthTaskIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private String token;

    @BeforeEach
    void setup() {

        User user =
                userRepository.findByEmail(
                        "admin@gmail.com"
                );

        token =
                JwtUtil.generateToken(
                        user.getEmail(),
                        user.getRole()
                );
    }

    @Test
    void getTasksShouldReturn200WhenTokenValid() throws Exception {

        mockMvc.perform(
                        get("/tasks")
                                .header(
                                        "Authorization",
                                        "Bearer " + token
                                )
                )
                .andExpect(
                        status().isOk()
                );
    }
}