package com.zorvyn.finance.controller;

import com.zorvyn.finance.dto.user.UserResponse;
import com.zorvyn.finance.entity.Role;
import com.zorvyn.finance.entity.Status;
import com.zorvyn.finance.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }


    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long id,
            @RequestParam Status status) {

        userService.updateStatus(id, status);
        return ResponseEntity.ok("Status updated");
    }


    @PatchMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateRole(
            @PathVariable Long id,
            @RequestParam Role role) {

        userService.updateRole(id, role);
        return ResponseEntity.ok("Role updated");
    }
}