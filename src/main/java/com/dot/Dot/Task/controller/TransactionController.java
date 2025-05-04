package com.dot.Dot.Task.controller;

import com.dot.Dot.Task.dto.TransactionRequestDto;
import com.dot.Dot.Task.dto.TransactionResponseDto;
import com.dot.Dot.Task.payload.ApiResponse;
import com.dot.Dot.Task.service.impl.TransactionServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private TransactionServiceImpl transactionService;
    public TransactionController(TransactionServiceImpl transactionService){
        this.transactionService = transactionService;
    }
    @PostMapping
    public ResponseEntity<ApiResponse<TransactionResponseDto>> createTransaction(
            @Valid @RequestBody TransactionRequestDto transactionRequestDto) {

        TransactionResponseDto result = transactionService.createTransaction(transactionRequestDto);
        return ResponseEntity.ok(ApiResponse.success("Transaction created Successfully", result));
    }

}
