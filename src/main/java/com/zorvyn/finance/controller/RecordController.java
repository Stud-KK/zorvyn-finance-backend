package com.zorvyn.finance.controller;

import com.zorvyn.finance.dto.record.RecordRequest;
import com.zorvyn.finance.dto.record.RecordResponse;
import com.zorvyn.finance.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/records")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping("/debug")
    public String debug(Authentication auth) {
        return auth.getAuthorities().toString();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public RecordResponse createRecord(@RequestBody RecordRequest dto,
                                       Authentication authentication) {

        String email = authentication.getName();
        return recordService.createRecord(dto, email);
    }
}