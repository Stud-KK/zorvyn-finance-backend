package com.zorvyn.finance.service;

import com.zorvyn.finance.dto.record.RecordRequest;
import com.zorvyn.finance.dto.record.RecordResponse;
import com.zorvyn.finance.entity.Record;
import com.zorvyn.finance.entity.User;
import com.zorvyn.finance.repository.RecordRepository;
import com.zorvyn.finance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RecordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecordRepository recordRepository;

    public RecordResponse createRecord(RecordRequest dto, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Record record = Record.builder()
                .amount(dto.getAmount())
                .type(dto.getType())
                .category(dto.getCategory())
                .note(dto.getNote())
                .date(LocalDate.now())
                .user(user)
                .build();

        Record saved = recordRepository.save(record);

        return RecordResponse.builder()
                .id(saved.getId())
                .amount(saved.getAmount())
                .type(saved.getType())
                .category(saved.getCategory())
                .date(saved.getDate())
                .note(saved.getNote())
                .build();
    }
}