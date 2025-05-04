package com.dot.Dot.Task.repository;

import com.dot.Dot.Task.entity.DailyTransactionSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyTransactionSummaryRepository extends JpaRepository<DailyTransactionSummary, Long> {
    Optional<DailyTransactionSummary> findByDate(LocalDate date);
}
