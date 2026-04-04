package com.zorvyn.finance.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ANALYST')")
    @GetMapping("/test-dashboard")
    public String testDashboard() {
        return "Dashboard data";
    }
}
