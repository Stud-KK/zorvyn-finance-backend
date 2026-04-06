package com.zorvyn.finance.service;

import com.zorvyn.finance.dto.record.RecordRequest;
import com.zorvyn.finance.dto.record.RecordResponse;
import com.zorvyn.finance.entity.Record;
import com.zorvyn.finance.entity.RecordType;
import com.zorvyn.finance.entity.User;
import com.zorvyn.finance.repository.RecordRepository;
import com.zorvyn.finance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecordService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RecordRepository recordRepository;

    // CREATE
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

        return mapToResponse(saved);
    }

    // GET ALL
    public List<RecordResponse> getUserRecords(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return recordRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // FILTER BY TYPE
    public List<RecordResponse> filterByType(String email, RecordType type) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return recordRepository.findByUserAndType(user, type)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // FILTER BY CATEGORY
    public List<RecordResponse> filterByCategory(String email, String category) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return recordRepository.findByUserAndCategory(user, category)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // FILTER BY DATE
    public List<RecordResponse> filterByDate(String email, LocalDate start, LocalDate end) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return recordRepository.findByUserAndDateBetween(user, start, end)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    public RecordResponse updateRecord(Long id, RecordRequest dto, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Record record = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));


        if (!record.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        record.setAmount(dto.getAmount());
        record.setType(dto.getType());
        record.setCategory(dto.getCategory());
        record.setNote(dto.getNote());
        record.setDate(LocalDate.now());

        return mapToResponse(recordRepository.save(record));
    }


    public void deleteRecord(Long id, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Record record = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));


        if (!record.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        recordRepository.delete(record);
    }


    private RecordResponse mapToResponse(Record r) {
        return RecordResponse.builder()
                .id(r.getId())
                .amount(r.getAmount())
                .type(r.getType())
                .category(r.getCategory())
                .date(r.getDate())
                .note(r.getNote())
                .build();
    }
}