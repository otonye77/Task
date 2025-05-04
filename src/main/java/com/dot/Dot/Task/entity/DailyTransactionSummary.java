package com.dot.Dot.Task.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DailyTransactionSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private Integer totalTransactions;

    private BigDecimal totalAmount;

    private BigDecimal totalCommission;

    public DailyTransactionSummary(LocalDate date, Integer totalTransactions, BigDecimal totalAmount, BigDecimal totalCommission) {
        this.date = date;
        this.totalTransactions = totalTransactions;
        this.totalAmount = totalAmount;
        this.totalCommission = totalCommission;
    }


}
