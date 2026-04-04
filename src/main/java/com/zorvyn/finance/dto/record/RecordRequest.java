package com.zorvyn.finance.dto.record;

import com.zorvyn.finance.entity.RecordType;
import lombok.Data;

@Data
public class RecordRequest {

    private Double amount;
    private RecordType type;
    private String category;
    private String note;
}