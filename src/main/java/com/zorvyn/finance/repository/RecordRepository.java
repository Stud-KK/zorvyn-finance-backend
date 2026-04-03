package com.zorvyn.finance.repository;

import com.zorvyn.finance.entity.Record;
import com.zorvyn.finance.entity.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findByType(RecordType type);

    List<Record> findByCategory(String category);

    List<Record> findByDateBetween(LocalDate start, LocalDate end);
}