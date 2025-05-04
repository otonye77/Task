package com.dot.Dot.Task.controller;

import com.dot.Dot.Task.dto.TransactionResponseDto;
import com.dot.Dot.Task.dto.TransferRequestDto;
import com.dot.Dot.Task.dto.TransferResponseDto;
import com.dot.Dot.Task.entity.DailyTransactionSummary;
import com.dot.Dot.Task.enums.TransactionStatus;
import com.dot.Dot.Task.repository.DailyTransactionSummaryRepository;
import com.dot.Dot.Task.service.impl.TransferServiceImpl;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TransferController {
    private final TransferServiceImpl transferService;
    private final DailyTransactionSummaryRepository summaryRepo;

    public TransferController(TransferServiceImpl transferService, DailyTransactionSummaryRepository summaryRepo) {
        this.transferService = transferService;
        this.summaryRepo = summaryRepo;
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponseDto> transfer(@Valid @RequestBody TransferRequestDto request){
        TransferResponseDto response = transferService.processTransfer(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionResponseDto>> getTransactions(
            @RequestParam(required = false) TransactionStatus status,
            @RequestParam(required = false) String fromAccount,
            @RequestParam(required = false)LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
            ) {
        List<TransactionResponseDto> transactions = transferService.getTransactions(status, fromAccount, startDate, endDate);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{date}")
    public DailyTransactionSummary getSummaryByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return summaryRepo.findByDate(date)
                .orElseThrow(() -> new RuntimeException("Summary not found for date: " + date));
    }
}
