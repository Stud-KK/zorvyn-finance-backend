package com.zorvyn.finance.service;

import com.zorvyn.finance.dto.auth.LoginRequest;
import com.zorvyn.finance.dto.auth.RegisterRequest;
import com.zorvyn.finance.entity.Role;
import com.zorvyn.finance.entity.Status;
import com.zorvyn.finance.entity.User;
import com.zorvyn.finance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public String register(RegisterRequest request) {

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword()) // later encode
                .role(Role.ROLE_VIEWER)
                .status(Status.ACTIVE)
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return "Login successful";
    }
}