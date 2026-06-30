package com.bhakti.backend_api.integration;

import com.bhakti.backend_api.entity.User;
import com.bhakti.backend_api.repository.UserRepository;
import com.bhakti.backend_api.security.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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
                new User();

        user.setName(
                "Admin Test"
        );

        user.setEmail(
                "admin@test.com"
        );

        user.setPassword(
        "123456"
);

       user.setRole(
        "ADMIN"
);

        user =
                userRepository.save(
                        user
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