package com.bhakti.backend_api.service;

import com.bhakti.backend_api.dto.LoginRequest;
import com.bhakti.backend_api.dto.RegisterRequest;
import com.bhakti.backend_api.dto.RegisterResponse;
import com.bhakti.backend_api.entity.User;
import com.bhakti.backend_api.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    public AuthServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginShouldReturnErrorWhenUserNotFound() {

        LoginRequest request =
                new LoginRequest();

        request.setEmail(
                "test@gmail.com"
        );

        request.setPassword(
                "123456"
        );

        when(
                userRepository.findByEmail(
                        "test@gmail.com"
                )
        ).thenReturn(null);

        String result =
                authService.login(
                        request
                );

        assertEquals(
                "Invalid email or password",
                result
        );
    }

    @Test
    void loginShouldReturnErrorWhenPasswordIsWrong() {

        LoginRequest request =
                new LoginRequest();

        request.setEmail(
                "test@gmail.com"
        );

        request.setPassword(
                "salah123"
        );

        User user =
                new User();

        user.setEmail(
                "test@gmail.com"
        );

        user.setPassword(
                new BCryptPasswordEncoder()
                        .encode("benar123")
        );

        when(
                userRepository.findByEmail(
                        "test@gmail.com"
                )
        ).thenReturn(user);

        String result =
                authService.login(
                        request
                );

        assertEquals(
                "Invalid email or password",
                result
        );
    }

    @Test
void loginShouldReturnTokenWhenPasswordIsCorrect() {

    LoginRequest request =
            new LoginRequest();

    request.setEmail(
            "test@gmail.com"
    );

    request.setPassword(
            "benar123"
    );

    User user =
            new User();

    user.setEmail(
            "test@gmail.com"
    );

    user.setRole(
            "USER"
    );

    user.setPassword(
            new BCryptPasswordEncoder()
                    .encode("benar123")
    );

    when(
            userRepository.findByEmail(
                    "test@gmail.com"
            )
    ).thenReturn(user);

    String result =
            authService.login(
                    request
            );

    assertEquals(
            false,
            result.equals(
                    "Invalid email or password"
            )
    );
}

@Test
void registerShouldCreateUserSuccessfully() {

    RegisterRequest request =
            new RegisterRequest();

    request.setName(
            "Miko"
    );

    request.setEmail(
            "miko@gmail.com"
    );

    request.setPassword(
            "123456"
    );

    User savedUser =
            new User();

    savedUser.setName(
            "Miko"
    );

    savedUser.setEmail(
            "miko@gmail.com"
    );

    savedUser.setRole(
            "USER"
    );

    when(
            userRepository.save(
                    any(User.class)
            )
    ).thenReturn(savedUser);

    RegisterResponse result =
            authService.register(
                    request
            );

    assertEquals(
            "Miko",
            result.getName()
    );

    assertEquals(
            "miko@gmail.com",
            result.getEmail()
    );

    assertEquals(
            "USER",
            result.getRole()

            
    );
}

}