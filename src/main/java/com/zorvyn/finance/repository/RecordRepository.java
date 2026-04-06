package com.zorvyn.finance.repository;

import com.zorvyn.finance.entity.Record;
import com.zorvyn.finance.entity.RecordType;
import com.zorvyn.finance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findByUser(User user);
    List<Record> findByUserAndType(User user, RecordType type);
    List<Record> findByUserAndCategory(User user, String category);
    List<Record> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);



    @Query("SELECT SUM(r.amount) FROM Record r WHERE r.user = :user AND r.type = 'INCOME'")
    Double getTotalIncome(User user);

    @Query("SELECT SUM(r.amount) FROM Record r WHERE r.user = :user AND r.type = 'EXPENSE'")
    Double getTotalExpense(User user);

    @Query("SELECT r.category, SUM(r.amount) FROM Record r WHERE r.user = :user AND r.type = 'INCOME' GROUP BY r.category")
    List<Object[]> getIncomeByCategory(User user);

    @Query("SELECT r.category, SUM(r.amount) FROM Record r WHERE r.user = :user AND r.type = 'EXPENSE' GROUP BY r.category")
    List<Object[]> getExpenseByCategory(User user);

    @Query("SELECT MONTH(r.date), SUM(r.amount) FROM Record r WHERE r.user = :user GROUP BY MONTH(r.date)")
    List<Object[]> getMonthlySummary(User user);

    @Query("SELECT COALESCE(SUM(r.amount),0) FROM Record r WHERE r.type = :type AND r.user = :user")
    Double sumByTypeAndUser(@Param("type") RecordType type, @Param("user") User user);














}