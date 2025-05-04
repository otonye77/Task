package com.dot.Dot.Task.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSummary {
    private LocalDate date;
    private int totalTransactions;
    private int successfulTransactions;
    private int failedTransactions;
    private BigDecimal totalAmountTransferred;
    private BigDecimal totalFees;
    private BigDecimal totalCommission;

    // Additional helper methods
    public BigDecimal getSuccessRate() {
        if (totalTransactions == 0) return BigDecimal.ZERO;
        return BigDecimal.valueOf(successfulTransactions)
                .divide(BigDecimal.valueOf(totalTransactions), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    public BigDecimal getAverageTransactionAmount() {
        if (successfulTransactions == 0) return BigDecimal.ZERO;
        return totalAmountTransferred.divide(
                BigDecimal.valueOf(successfulTransactions), 2, RoundingMode.HALF_UP);
    }
}