package com.dot.Dot.Task.service.impl;

import com.dot.Dot.Task.dto.TransactionRequestDto;
import com.dot.Dot.Task.dto.TransactionResponseDto;
import com.dot.Dot.Task.entity.Transaction;
import com.dot.Dot.Task.enums.TransactionStatus;
import com.dot.Dot.Task.repository.TransactionRepository;
import com.dot.Dot.Task.service.serviceinterface.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository repository;

    public TransactionServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransactionResponseDto createTransaction(TransactionRequestDto transactionRequestDto) {
        Transaction transaction = new Transaction();
        transaction.setTransactionReference(UUID.randomUUID().toString());
        transaction.setAmount(transactionRequestDto.getAmount());
        transaction.setDescription(transactionRequestDto.getDescription());
        transaction.setAccountNumber(transactionRequestDto.getAccountNumber());
        transaction.setCreatedDate(LocalDateTime.now());

        BigDecimal fee = transactionRequestDto.getAmount().multiply(BigDecimal.valueOf(0.005));
        fee = fee.compareTo(BigDecimal.valueOf(100)) > 0 ? BigDecimal.valueOf(100) : fee;

        transaction.setTransactionFee(fee);
        transaction.setBilledAmount(transactionRequestDto.getAmount().add(fee));
        transaction.setStatus(TransactionStatus.SUCCESSFUL);
        transaction.setStatusMessage("Transaction Processed Successfully");
        transaction.setCommisionWorthy(false);

        repository.save(transaction);
        return toResponseDto(transaction);
    }

    @Override
    public List<TransactionResponseDto> getAllTransactions() {
        List<Transaction> transactions = repository.findAll();
        List<TransactionResponseDto> responseList = transactions
                .stream()
                .map(transaction -> toResponseDto(transaction))
                .collect(Collectors.toList());
        return responseList;
    }

    @Override
    public TransactionResponseDto getTransactionByReference(String reference) {
        return null;
    }

    @Override
    public void deleteTransaction(String reference) {

    }

    private TransactionResponseDto toResponseDto(Transaction transaction) {
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
        transactionResponseDto.setTransactionReference(transaction.getTransactionReference());
        transactionResponseDto.setStatus(transaction.getStatus().toString());
        transactionResponseDto.setMessage(transaction.getStatusMessage());
        return transactionResponseDto;
    }
}
