package com.zorvyn.finance.controller;

import com.zorvyn.finance.dto.record.RecordRequest;
import com.zorvyn.finance.dto.record.RecordResponse;
import com.zorvyn.finance.entity.RecordType;
import com.zorvyn.finance.service.RecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/records")
public class RecordController {

    @Autowired
    private RecordService recordService;




    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public RecordResponse createRecord(@Valid @RequestBody RecordRequest dto,
                                       Authentication authentication) {

        String email = authentication.getName();
        return recordService.createRecord(dto, email);
    }


    // 🔹 GET ALL (user-specific)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public List<RecordResponse> getRecords(Authentication authentication) {

        String email = authentication.getName();
        return recordService.getUserRecords(email);
    }

    // FILTER BY TYPE
    @GetMapping("/filter/type")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public List<RecordResponse> filterByType(
            @RequestParam RecordType type,
            Authentication authentication) {

        String email = authentication.getName();
        return recordService.filterByType(email, type);
    }

    // FILTER BY CATEGORY
    @GetMapping("/filter/category")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public List<RecordResponse> filterByCategory(
            @RequestParam String category,
            Authentication authentication) {

        String email = authentication.getName();
        return recordService.filterByCategory(email, category);
    }

    // FILTER BY DATE RANGE
    @GetMapping("/filter/date")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public List<RecordResponse> filterByDate(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end,
            Authentication authentication) {

        String email = authentication.getName();
        return recordService.filterByDate(email, start, end);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RecordResponse updateRecord(
            @PathVariable Long id,
            @Valid @RequestBody RecordRequest dto,
            Authentication authentication) {

        String email = authentication.getName();
        return recordService.updateRecord(id, dto, email);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteRecord(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();
        recordService.deleteRecord(id, email);
        return "Record deleted successfully";
    }

}