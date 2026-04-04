package com.zorvyn.finance.dto.record;

import com.zorvyn.finance.entity.RecordType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RecordResponse {

    private Long id;
    private Double amount;
    private RecordType type;
    private String category;
    private LocalDate date;
    private String note;
}