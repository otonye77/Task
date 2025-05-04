package com.dot.Dot.Task.service.impl;

import com.dot.Dot.Task.dto.TransactionResponseDto;
import com.dot.Dot.Task.dto.TransactionSummary;
import com.dot.Dot.Task.dto.TransferRequestDto;
import com.dot.Dot.Task.dto.TransferResponseDto;
import com.dot.Dot.Task.entity.Account;
import com.dot.Dot.Task.entity.DailyTransactionSummary;
import com.dot.Dot.Task.entity.Transaction;
import com.dot.Dot.Task.enums.TransactionStatus;
import com.dot.Dot.Task.exception.AccountNotFoundException;
import com.dot.Dot.Task.repository.AccountRepository;
import com.dot.Dot.Task.repository.DailyTransactionSummaryRepository;
import com.dot.Dot.Task.repository.TransactionRepository;
import com.dot.Dot.Task.service.serviceinterface.TransferService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferServiceImpl implements TransferService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final DailyTransactionSummaryRepository dailySummaryRepo;

    public TransferServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository,  DailyTransactionSummaryRepository dailySummaryRepo) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.dailySummaryRepo = dailySummaryRepo;
    }

    @Override
    @Transactional
    public TransferResponseDto processTransfer(TransferRequestDto request) {
        Account fromAccount = accountRepository.findByAccountNumber(request.getFromAccount())
                .orElseThrow(() -> new AccountNotFoundException("Sender Account not found for account number: " + request.getFromAccount()));
        Account toAccount = accountRepository.findByAccountNumber(request.getToAccount())
                .orElseThrow(() -> new AccountNotFoundException("Receiver Account not found for account number: " + request.getToAccount()));

        if(fromAccount.getBalance().compareTo(request.getAmount()) < 0){
            throw new RuntimeException("Insufficient funds");
        }

        Transaction transaction = new Transaction();
        transaction.setTransactionReference(generateTransactionReference());
        transaction.setAmount(request.getAmount());

        BigDecimal transactionFee = calculateTransactionFee(request.getAmount());
        transaction.setTransactionFee(transactionFee);

        transaction.setBilledAmount(transaction.getAmount().add(transaction.getTransactionFee()));
        transaction.setDescription(request.getDescription());
        transaction.setFromAccount(request.getFromAccount());
        transaction.setToAccount(request.getToAccount());
        transaction.setCreatedDate(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.PENDING);

        BigDecimal commission = BigDecimal.ZERO;
        if (transaction.getStatus() == TransactionStatus.SUCCESSFUL) {
            commission = calculateCommission(transactionFee);
        }

        transaction.setCommisionWorthy(commission.compareTo(BigDecimal.ZERO) > 0);
        transaction.setCommision(commission);

        transactionRepository.save(transaction);

        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        transaction.setStatus(TransactionStatus.SUCCESSFUL);
        transaction.setStatusMessage("Transfer completed successfully");
        transactionRepository.save(transaction);

        TransferResponseDto response = new TransferResponseDto(
                transaction.getTransactionReference(),
                transaction.getFromAccount(),
                transaction.getToAccount(),
                transaction.getAmount(),
                transaction.getTransactionFee(),
                transaction.getBilledAmount(),
                transaction.getDescription(),
                transaction.getCreatedDate(),
                transaction.getStatus(),
                transaction.getStatusMessage(),
                transaction.isCommisionWorthy(),
                transaction.getCommision()
        );

        return response;

    }

    @Override
    public  DailyTransactionSummary getDailySummary(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);

        List<Transaction> transactions = transactionRepository.findByCreatedDateBetween(start, end);

        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalCommission = BigDecimal.ZERO;

        for (Transaction t : transactions) {
            totalAmount = totalAmount.add(t.getAmount());
            totalCommission = totalCommission.add(t.getCommision());
        }

        DailyTransactionSummary summary = new DailyTransactionSummary(
                date,
                transactions.size(),
                totalAmount,
                totalCommission
        );

        return dailySummaryRepo.save(summary);
    }

    @Override
    public void calculateCommissions() {
        List<Transaction> transactions = transactionRepository.findByStatus(TransactionStatus.SUCCESSFUL);
        for (Transaction transaction : transactions){
            if(!transaction.isCommisionWorthy()){
                BigDecimal transactionFee = calculateTransactionFee(transaction.getAmount());
                BigDecimal commission = calculateCommission(transactionFee);
                transaction.setTransactionFee(transactionFee);
                transaction.setCommision(commission);
                transaction.setCommisionWorthy(true);
                transactionRepository.save(transaction);
            }
        }
    }

    @Override
    public void generateDailySummary(LocalDate date) {

    }

    @Override
    public List<TransactionResponseDto> getTransactions(TransactionStatus status, String fromAccount, LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions;
        LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(23, 59, 59) : null;

        if (status != null && fromAccount != null && startDate != null && endDate != null) {
            transactions = transactionRepository.findByStatusAndFromAccountAndCreatedDateBetween(status, fromAccount, startDateTime, endDateTime);
        } else if (status != null) {
            transactions = transactionRepository.findByStatus(status);
        } else if (fromAccount != null) {
            transactions = transactionRepository.findByFromAccount(fromAccount);
        } else if (startDate != null && endDate != null) {
            transactions = transactionRepository.findByCreatedDateBetween(startDateTime, endDateTime);
        } else {
            transactions = transactionRepository.findAll();
        }

        return transactions.stream()
                .map(transaction -> new TransactionResponseDto(
                        transaction.getTransactionReference(),
                        transaction.getFromAccount(),
                        transaction.getToAccount(),
                        transaction.getAmount(),
                        transaction.getTransactionFee(),
                        transaction.getBilledAmount(),
                        transaction.getDescription(),
                        transaction.getCreatedDate(),
                        transaction.getStatus(),
                        transaction.getStatusMessage(),
                        transaction.isCommisionWorthy(),
                        transaction.getCommision()
                ))
                .collect(Collectors.toList());
    }


    private String generateTransactionReference() {
        return "TXN-" + System.currentTimeMillis();
    }

    private BigDecimal calculateTransactionFee(BigDecimal amount) {
        BigDecimal fee = amount.multiply(BigDecimal.valueOf(0.005));
        return fee.min(BigDecimal.valueOf(100));
    }

    private BigDecimal calculateCommission(BigDecimal transactionFee) {
        return transactionFee.multiply(BigDecimal.valueOf(0.2));
    }


}
