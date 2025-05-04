package com.dot.Dot.Task.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransactionRequestDto {
    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Amount must be greater than 0")
    public BigDecimal amount;
    @NotBlank(message = "Description is required")
    public String description;
    @NotBlank(message = "Account number is required")
    public String accountNumber;
}
