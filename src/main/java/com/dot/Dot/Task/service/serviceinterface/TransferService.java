package com.dot.Dot.Task.service.serviceinterface;

import com.dot.Dot.Task.dto.TransactionResponseDto;
import com.dot.Dot.Task.dto.TransactionSummary;
import com.dot.Dot.Task.dto.TransferRequestDto;
import com.dot.Dot.Task.dto.TransferResponseDto;
import com.dot.Dot.Task.entity.DailyTransactionSummary;
import com.dot.Dot.Task.enums.TransactionStatus;

import java.time.LocalDate;
import java.util.List;

public interface TransferService {
    TransferResponseDto processTransfer(TransferRequestDto request);
    DailyTransactionSummary getDailySummary(LocalDate date);
    void calculateCommissions();
    void generateDailySummary(LocalDate date);
    List<TransactionResponseDto> getTransactions(TransactionStatus status, String fromAccount, LocalDate startDate, LocalDate endDate);
}
