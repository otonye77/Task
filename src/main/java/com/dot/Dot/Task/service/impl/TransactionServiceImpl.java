package com.dot.Dot.Task.service.impl;

import com.dot.Dot.Task.dto.TransactionRequestDto;
import com.dot.Dot.Task.dto.TransactionResponseDto;
import com.dot.Dot.Task.repository.TransactionRepository;
import com.dot.Dot.Task.service.serviceinterface.TransactionService;

import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository repository;

    public TransactionServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransactionResponseDto createTransaction(TransactionRequestDto transactionRequestDto) {
        return null;
    }

    @Override
    public List<TransactionResponseDto> getAllTransactions() {
        return List.of();
    }

    @Override
    public TransactionResponseDto getTransactionByReference(String reference) {
        return null;
    }

    @Override
    public void deleteTransaction(String reference) {

    }
}
