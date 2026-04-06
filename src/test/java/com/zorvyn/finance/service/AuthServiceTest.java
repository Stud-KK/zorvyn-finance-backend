package com.zorvyn.finance.service;

import com.zorvyn.finance.dto.auth.LoginRequest;
import com.zorvyn.finance.entity.Status;
import com.zorvyn.finance.entity.User;
import com.zorvyn.finance.repository.UserRepository;
import com.zorvyn.finance.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Test
    void testLogin_success() {

        // mock dependencies
        UserRepository userRepo = mock(UserRepository.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);

        AuthService service = new AuthService();
        service.userRepository = userRepo;
        service.jwtUtil = jwtUtil;
        service.passwordEncoder = encoder;

        // dummy user
        User user = User.builder()
                .email("test@gmail.com")
                .password("hashed")
                .status(Status.ACTIVE)
                .build();

        LoginRequest req = new LoginRequest();
        req.setEmail("test@gmail.com");
        req.setPassword("1234");

        // mock behavior
        when(userRepo.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(encoder.matches("1234", "hashed"))
                .thenReturn(true);

        when(jwtUtil.generateToken("test@gmail.com"))
                .thenReturn("token123");

        // call
        String result = service.login(req);

        // verify
        assertEquals("token123", result);
    }
}