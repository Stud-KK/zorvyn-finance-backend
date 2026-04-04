package com.zorvyn.finance.controller;

import com.zorvyn.finance.dto.auth.LoginRequest;
import com.zorvyn.finance.dto.auth.RegisterRequest;
import com.zorvyn.finance.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")

    public String login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }


}