package com.zorvyn.finance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity

@Table(name = "records", indexes = {
        @Index(name = "idx_user", columnList = "user_id"),
        @Index(name = "idx_date", columnList = "date")
})

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecordType type; // INCOME / EXPENSE

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private LocalDate date;

    private String note;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}