package com.dot.Dot.Task.repository;

import com.dot.Dot.Task.entity.Transaction;
import com.dot.Dot.Task.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByStatus(TransactionStatus status);

    List<Transaction> findByFromAccount(String fromAccount);

    List<Transaction> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Transaction> findByStatusAndFromAccountAndCreatedDateBetween(TransactionStatus status, String fromAccount, LocalDateTime startDate, LocalDateTime endDate);
}
