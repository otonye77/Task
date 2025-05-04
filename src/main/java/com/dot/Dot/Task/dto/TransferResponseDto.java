package com.dot.Dot.Task.dto;

import com.dot.Dot.Task.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponseDto {
    private String transactionRef;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private BigDecimal transactionFee;
    private BigDecimal billedAmount;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private TransactionStatus status;
    private String statusMessage;

    private boolean commissionWorthy;
    private BigDecimal commissionAmount;
}
