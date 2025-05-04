package com.dot.Dot.Task.repository;

import com.dot.Dot.Task.entity.Transaction;
import com.dot.Dot.Task.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByStatus(TransactionStatus status);
    List<Transaction> findByAccountNumber(String accountNumber);
    List<Transaction> findByDateCreatedBetween(LocalDateTime start, LocalDateTime end);
}
