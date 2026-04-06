package com.zorvyn.finance.dto.record;

import com.zorvyn.finance.entity.RecordType;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class RecordRequest {




        @NotNull
        @Positive
        private Double amount;

        @NotNull
        private RecordType type;

        @NotBlank
        private String category;

        private String note;
    }
