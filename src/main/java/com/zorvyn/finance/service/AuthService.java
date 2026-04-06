package com.zorvyn.finance.service;

import com.zorvyn.finance.dto.auth.LoginRequest;
import com.zorvyn.finance.dto.auth.RegisterRequest;
import com.zorvyn.finance.entity.Role;
import com.zorvyn.finance.entity.Status;
import com.zorvyn.finance.entity.User;
import com.zorvyn.finance.repository.UserRepository;
import com.zorvyn.finance.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    protected JwtUtil jwtUtil;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    public String register(RegisterRequest request) {

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_VIEWER) // default role
                .status(Status.ACTIVE)  // default active
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));


        if (user.getStatus() == Status.INACTIVE) {
            throw new RuntimeException("User is inactive");
        }


        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}