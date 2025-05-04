package com.dot.Dot.Task.service.serviceinterface;

import com.dot.Dot.Task.dto.TransactionRequestDto;
import com.dot.Dot.Task.dto.TransactionResponseDto;

import java.util.List;

public interface TransactionService {
    TransactionResponseDto createTransaction(TransactionRequestDto transactionRequestDto);
    List<TransactionResponseDto> getAllTransactions();
    TransactionResponseDto getTransactionByReference(String reference);
    void deleteTransaction(String reference);
}
