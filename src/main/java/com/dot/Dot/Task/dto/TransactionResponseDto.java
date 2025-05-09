package com.dot.Dot.Task.dto;

import com.dot.Dot.Task.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDto {
    private String transactionReference;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private BigDecimal transactionFee;
    private BigDecimal billedAmount;
    private String description;
    private LocalDateTime createdDate;
    private TransactionStatus status;
    private String statusMessage;
    private boolean isCommisionWorthy;
    private BigDecimal commision;
}
